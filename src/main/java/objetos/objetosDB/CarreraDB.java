package objetos.objetosDB;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import objetos.enums.Estado;
import objetos.enums.EstadoCarrera;
import objetos.objetosResponse.AsignacionEstudiantes;
import objetos.objetosResponse.Carrera;
import objetos.objetosResponse.Seccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarreraDB {

    public List<Carrera> todosLasCarreras() throws SQLException {
        List<Carrera> carreraRespons = new ArrayList<>();
        String sqlSolicitarCarreras = "SELECT * FROM Carrera";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarCarreras);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                carreraRespons.add(crearCarrera(resultSet));
            }
        }
        return carreraRespons;
    }
    public void cambiarEstadoCarrera(int idCarrera, EstadoCarrera estadoCarrera) throws SQLException{
        String sqlDeshabilitar = "UPDATE Carrera SET estadoCarrera = ? WHERE idCarrera = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDeshabilitar)){
            preparedStatement.setString(1,estadoCarrera.name());
            preparedStatement.setInt(2,idCarrera);
            preparedStatement.executeUpdate();
        }
    }
    public void ingresarNuevaCarrera(String nombreCarrera) throws SQLException{
        String sqlIngresarCarrera = "INSERT INTO Carrera(nombreCarrera) VALUE (?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarCarrera)){
            preparedStatement.setString(1,nombreCarrera);
            preparedStatement.executeUpdate();
        }
    }
    private Carrera crearCarrera(ResultSet resultSet) throws SQLException {
        int idCarrera = resultSet.getInt("idCarrera");
        String nombreCarrera = resultSet.getString("nombreCarrera");
        EstadoCarrera estadoCarrera = EstadoCarrera.valueOf(resultSet.getString("estadoCarrera"));

        return new Carrera(idCarrera, nombreCarrera, estadoCarrera);
    }
}