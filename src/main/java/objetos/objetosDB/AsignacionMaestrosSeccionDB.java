package objetos.objetosDB;

import objetos.objetosRequest.AsignarMaestroRequest;
import objetos.objetosResponse.AsignacionMaestrosSeccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AsignacionMaestrosSeccionDB {

    public void ingresarNuevaAsignacionMaestroSeccion(AsignarMaestroRequest asignacion) throws SQLException {
        String sqlAsignarMaestroASeccion = "INSERT INTO AsignacionMaestrosSeccion(idSeccion, idMaestro, idCurso) VALUES(?,?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlAsignarMaestroASeccion)){
            preparedStatement.setInt(1, Integer.parseInt(asignacion.getIdSeccion()));
            preparedStatement.setInt(1, Integer.parseInt(asignacion.getIdMaestro()));
            preparedStatement.setInt(1, Integer.parseInt(asignacion.getIdCurso()));
            preparedStatement.executeUpdate();
        }
    }
}
