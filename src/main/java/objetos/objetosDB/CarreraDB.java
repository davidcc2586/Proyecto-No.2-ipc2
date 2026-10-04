package objetos.objetosDB;

import objetos.enums.EstadoCarrera;
import objetos.objetosClases.Carrera;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarreraDB {

    public List<Carrera> todosLasCarreras() throws SQLException {
        Connection connection = DBConnectionSingleton.getInstance().getConnection();
        List<Carrera> carreras = new ArrayList<>();
        String sqlSolicitarCarreras = "SELECT * FROM Carrera";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarCarreras);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                carreras.add(crearCarrera(resultSet));
            }
        }

        return carreras;
    }

    private Carrera crearCarrera(ResultSet resultSet) throws SQLException {
        int idCarrera = resultSet.getInt("idCarrera");
        String nombreCarrera = resultSet.getString("nombreCarrera");

        String estadoString = resultSet.getString("estadoCarrera");
        EstadoCarrera estadoCarrera = (estadoString != null) ? EstadoCarrera.valueOf(estadoString) : null;

        return new Carrera(idCarrera, nombreCarrera, estadoCarrera);
    }
}