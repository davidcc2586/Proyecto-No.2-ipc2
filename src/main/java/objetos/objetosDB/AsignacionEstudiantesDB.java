package objetos.objetosDB;

import objetos.objetosRequest.AsignacionEstudianteRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AsignacionEstudiantesDB {
    public void asignarEstudiante(AsignacionEstudianteRequest asignacion) throws SQLException {
        String sqlIngresarAsignacion = "INSERT INTO AsignacionEstudiantes(idseccion, idestudiante) VALUES(?,?)";
        try(Connection connection = ConnectionDB.getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarAsignacion)) {
            preparedStatement.setInt(1, Integer.parseInt(asignacion.getIdSeccion()));
            preparedStatement.setInt(2, Integer.parseInt(asignacion.getIdEstudiante()));
            preparedStatement.executeUpdate();
        }
    }

    public boolean isEstudianteAsignado(AsignacionEstudianteRequest asignacion) throws SQLException {
        String sql = "SELECT COUNT(*) FROM AsignacionEstudiantes WHERE idSeccion = ? AND idEstudiante = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, Integer.parseInt(asignacion.getIdSeccion()));
            preparedStatement.setInt(2, Integer.parseInt(asignacion.getIdEstudiante()));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
                return false;
            }
        }
    }
}
