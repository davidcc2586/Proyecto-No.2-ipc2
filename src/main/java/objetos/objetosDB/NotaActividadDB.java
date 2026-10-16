package objetos.objetosDB;

import objetos.objetosResponse.NotaActividad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotaActividadDB {
    public void ingresarNuevoNotaActividad(NotaActividad notaActividadResponse) throws SQLException{
        String sqlIngresarNota = "insert into NotaActividad(idActividad, idEstudiante, punteoObtenido) VALUES(?,?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarNota)){
            preparedStatement.setInt(1, notaActividadResponse.getIdActividad());
            preparedStatement.setInt(2, notaActividadResponse.getIdEstudiante());
            preparedStatement.setDouble(2, notaActividadResponse.getPunteoObtenido());
            preparedStatement.executeUpdate();
        }
    }
    private NotaActividad crearNotaActividad(ResultSet resultSet) throws SQLException {
        int idActividad = resultSet.getInt("idActividad");
        int idEstudiante = resultSet.getInt("idEstudiante");
        double punteoObtenido = resultSet.getDouble("punteoObtenido");
        return new NotaActividad(idActividad,idEstudiante,punteoObtenido);
    }
}
