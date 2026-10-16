package objetos.objetosDB;

import objetos.enums.GradoColegio;
import objetos.objetosResponse.AsignacionEstudiantes;
import objetos.objetosResponse.Carrera;
import objetos.objetosResponse.Grado;
import objetos.objetosResponse.Seccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradoDB {

    public List<Grado> todosLosGrados() throws SQLException {
        List<Grado> gradoResponses = new ArrayList<>();
        String sqlSolicitarGrados = "SELECT * FROM Grado";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarGrados);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                gradoResponses.add(crearGrado(resultSet));
            }
        }

        return gradoResponses;
    }
    public void ingresarNuevoGrado(Grado grado) throws SQLException{
        String sqlIngresarGrado = "insert into Grado(nombreGrado, grado, idCarrera) VALUES(?,?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarGrado)){
            preparedStatement.setString(1, grado.getGradoColegio().name());
            preparedStatement.setString(2, grado.getNombreGrado());
            preparedStatement.setInt(3, grado.getIdCarrera());
            preparedStatement.executeUpdate();
        }
    }

    public Grado gradoEstudiante(int idEstudiante, int anio) throws SQLException{
        List<AsignacionEstudiantes> asignaciones = new ArrayList<>();
        try (Connection connection = ConnectionDB.getConexion()){
            String sql = "SELECT * FROM AsignacionEstudiantes where  idEstudiante = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,idEstudiante);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                asignaciones.add(new AsignacionEstudiantes(resultSet.getInt("idSeccion"), idEstudiante));
            }
        }
        if (asignaciones.isEmpty()){
            throw new SQLException("Usuario no encontrado.");
        }

        Seccion seccion = null;
        try (Connection connection = ConnectionDB.getConexion()){
            String sql = "select * from Seccion where  idSeccion = ? and anio = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (AsignacionEstudiantes asinacion : asignaciones){
                preparedStatement.setInt(1,asinacion.getIdSeccion());
                preparedStatement.setInt(2,anio);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    SeccionDB seccionDB = new SeccionDB();
                    seccion = seccionDB.crearSeccion(resultSet);
                    break;
                }
            }
        }
        if (seccion == null){
            throw new SQLException("Usuario no encontrado.");
        }

        try (Connection connection = ConnectionDB.getConexion()){
            String sql = "SELECT * FROM Grado where idGrado = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,seccion.getIdGrado());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return crearGrado(resultSet);
            }else {
                throw new SQLException("Usuario no encontrado.");
            }
        }
    }

    private Grado crearGrado(ResultSet resultSet) throws SQLException{
        int idGrado = resultSet.getInt("idGrado");
        String nombreGrado = resultSet.getString("nombreGrado");
        GradoColegio gradoColegio = GradoColegio.valueOf(resultSet.getString("grado"));
        int idCarrera = resultSet.getInt("idCarrera");
        return new Grado(idGrado, nombreGrado, gradoColegio, idCarrera);
    }
}
