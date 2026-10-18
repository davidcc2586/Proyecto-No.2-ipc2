package objetos.objetosDB;

import objetos.objetosRequest.AsignacionEstudianteRequest;
import objetos.objetosResponse.AsignacionEstudiantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AsignacionEstudiantesDB {

    public void desasignarEstudiante(int idEstudiante) throws SQLException{
        String sqlAsignarEstudiante = "DELETE FROM AsignacionEstudiantes WHERE idEstudiante = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlAsignarEstudiante)){
            preparedStatement.setInt(1,idEstudiante);
            preparedStatement.executeUpdate();
        }
    }

    public void asignarEstudiante(AsignacionEstudianteRequest asignacion) throws SQLException {
        String sqlIngresarAsignacion = "INSERT INTO AsignacionEstudiantes(idseccion, idestudiante) VALUES(?,?)";
        try(Connection connection = ConnectionDB.getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarAsignacion)) {
            preparedStatement.setInt(1, Integer.parseInt(asignacion.getIdSeccion()));
            preparedStatement.setInt(2, Integer.parseInt(asignacion.getIsEstudiante()));
            preparedStatement.executeUpdate();
        }
    }

    private AsignacionEstudiantes crearAsignacionEstudiate(ResultSet resultSet)throws SQLException{
        int idSeccion = resultSet.getInt("idSeccion");
        int idEstudiante = resultSet.getInt("idEstudiante");
        return new AsignacionEstudiantes(idSeccion,idEstudiante);
    }
}
