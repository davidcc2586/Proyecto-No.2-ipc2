package objetos.objetosDB;

import objetos.enums.TipoActividad;
import objetos.objetosRequest.ActividadRequest;
import objetos.objetosResponse.Actividad;
import objetos.objetosResponse.Estudiante;

import java.sql.*;
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
    public void ingresarNuevaActividadParaCursoSeccion(ActividadRequest actividadRequest) throws SQLException{
        try (Connection connection = ConnectionDB.getConexion()) {

            try {
                connection.setAutoCommit(false);

                int idActividad;
                String sqlIngresarNuevaActividad = "INSERT INTO Actividad(idseccion, idcurso, nombreactividad, ponderacion, tipo) VALUES(?,?,?,?,?)";
                try ( PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarNuevaActividad, Statement.RETURN_GENERATED_KEYS)){
                    preparedStatement.setInt(1, Integer.parseInt(actividadRequest.getIdSeccion()));
                    preparedStatement.setInt(2, Integer.parseInt(actividadRequest.getIdCurso()));
                    preparedStatement.setString(3, actividadRequest.getNombreActividad());
                    preparedStatement.setDouble(4, Double.parseDouble(actividadRequest.getPonderacion()));
                    preparedStatement.setString(5, actividadRequest.getTipoActividad());
                    preparedStatement.executeUpdate();
                    ResultSet resultSet = preparedStatement.getGeneratedKeys();
                    if (resultSet.next()) {
                        idActividad = resultSet.getInt(1);
                    }else {
                        throw new SQLException();
                    }
                }

                List<Integer> idEstudiantesSeccion = idEstudiantesSeccion(Integer.parseInt(actividadRequest.getIdSeccion()));
                String sqlCrearNotasEstudiantes = "INSERT INTO NotaActividad(idActividad, idEstudiante) VALUES (?,?)";
                try ( PreparedStatement preparedStatement = connection.prepareStatement(sqlCrearNotasEstudiantes);){
                    preparedStatement.setInt(1, idActividad);
                    for (int idEstudiante : idEstudiantesSeccion){
                        preparedStatement.setInt(2,idEstudiante);
                        preparedStatement.executeUpdate();
                    }
                }

                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    private List<Integer>  idEstudiantesSeccion(int idSeccion) throws SQLException{
        List<Integer> idEstudiantes = new ArrayList<>();
        EstudianteDB estudianteDB = new EstudianteDB();
        for (Estudiante estudiante : estudianteDB.estudiantesSeccion(idSeccion)){
            idEstudiantes.add(estudiante.getIdEstudiante());
        }
        return idEstudiantes;
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
