package objetos.objetosDB;

import objetos.objetosResponse.AsignacionMaestrosSeccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AsignacionMaestrosSeccionDB {

    public void ingresarNuevaAsignacionMaestroSeccion(AsignacionMaestrosSeccion asignacionMaestrosSeccion) throws SQLException {
        String sqlAsignarMaestroASeccion = "INSERT INTO AsignacionMaestrosSeccion(idSeccion, idMaestro, idCurso) VALUES(?,?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlAsignarMaestroASeccion)){
            preparedStatement.setInt(1, asignacionMaestrosSeccion.getIdSeccion());
            preparedStatement.setInt(1, asignacionMaestrosSeccion.getIdMaestro());
            preparedStatement.setInt(1, asignacionMaestrosSeccion.getIdCurso());
            preparedStatement.executeUpdate();
        }
    }
}
