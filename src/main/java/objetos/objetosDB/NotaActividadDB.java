package objetos.objetosDB;

import objetos.objetosClases.NotaActividad;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotaActividadDB {

    private Connection connection;

    private NotaActividad crearNotaActividad(ResultSet resultSet) throws SQLException {
        int idActividad = resultSet.getInt("idActividad");
        int idEstudiante = resultSet.getInt("idEstudiante");
        double punteoObtenido = resultSet.getDouble("punteoObtenido");
        return new NotaActividad(idActividad,idEstudiante,punteoObtenido);
    }
}
