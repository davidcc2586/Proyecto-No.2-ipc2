package objetos.objetosDB;

import objetos.enums.TipoActividad;
import objetos.objetosResponse.Actividad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActividadDB {

    public List<Actividad> actividadesDelCursoSeccion(int idCurso, int idSeccion) throws SQLException {

        List<Actividad> actividades = new ArrayList<>();
        String sqlSolicitarActividades = "SELECT * FROM Actividad WHERE idCurso = ? AND idSeccion = ?";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarActividades)) {
            preparedStatement.setInt(1, idCurso);
            preparedStatement.setInt(2, idSeccion);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                actividades.add(crearActividad(resultSet));
            }
            resultSet.close();
        }

        return actividades;
    }
    public void ingresarNuevaActividadParaCursoSeccion(Actividad actividadRequest) throws SQLException{
        String sqlIngresarNuevaActividad = "INSERT INTO Actividad(idseccion, idcurso, nombreactividad, ponderacion, tipo) VALUES(?,?,?,?,?)";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarNuevaActividad)) {
            preparedStatement.setInt(1, actividadRequest.getIdSeccion());
            preparedStatement.setInt(2, actividadRequest.getIdCurso());
            preparedStatement.setString(3, actividadRequest.getNombreActividad());
            preparedStatement.setDouble(4, actividadRequest.getPonderacion());
            preparedStatement.setString(5, actividadRequest.getTipoActividad().name());
            preparedStatement.executeUpdate();
        }
    }

    public void eliminarActividad(int idActividad) throws SQLException{
        String sqlEliminarActividad = "DELETE FROM Actividad WHERE idActividad = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlEliminarActividad)){
             preparedStatement.setInt(1,idActividad);
             preparedStatement.executeUpdate();
        }
    }
    private Actividad crearActividad(ResultSet resultSet) throws SQLException{
        int idActividad = resultSet.getInt("idActividad");
        int idSeccion = resultSet.getInt("idSeccion");
        int idCurso = resultSet.getInt("idCurso");
        String nombreActividad = resultSet.getString("nombreActividad");
        double ponderacion = resultSet.getDouble("ponderacion");
        TipoActividad tipoActividad = TipoActividad.valueOf(resultSet.getString("tipo"));
        return new Actividad(idActividad,idSeccion,idCurso,nombreActividad,ponderacion,tipoActividad);
    }
}
