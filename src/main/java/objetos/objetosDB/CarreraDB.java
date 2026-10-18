package objetos.objetosDB;

import objetos.enums.Estado;
import objetos.enums.EstadoCarrera;
import objetos.objetosResponse.Carrera;
import objetos.objetosResponse.CarreraActualizarRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void activarDesactivarCarrera(int idCarrera) throws SQLException{
        try (Connection connection = ConnectionDB.getConexion()){
            Estado estadoCarrera;

            String sqlSolicitarEstado = "SELECT estado FROM Carrera WHERE idCarrera = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarEstado)){
                preparedStatement.setInt(1,idCarrera);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                estadoCarrera = Estado.valueOf(resultSet.getString("estado"));
            }

            if (estadoCarrera.equals(Estado.ACTIVO)){
                estadoCarrera = Estado.INACTIVO;
            } else {
                estadoCarrera = Estado.ACTIVO;
            }

            String sqlDeshabilitar = "UPDATE Carrera SET estado = ? WHERE idCarrera = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDeshabilitar)){
                preparedStatement.setString(1,estadoCarrera.name());
                preparedStatement.setInt(2,idCarrera);
                preparedStatement.executeUpdate();
            }
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

    public void actualizarCarrera(CarreraActualizarRequest carrera)throws SQLException{
        String sql = "UPDATE Carrera SET nombreCarrera = ? WHERE idCarrera = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, carrera.getNombreCarrera());
            preparedStatement.setInt(1,Integer.parseInt(carrera.getIdCarrera()));
            preparedStatement.executeUpdate();
        }
    }

    public Optional<Carrera> obtenerLibro(int idCarrera) throws SQLException{
        String sql = "SELECT * FROM Carrera WHERE idCarrera = ?";
        Optional<Carrera> carrera = Optional.empty();
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, idCarrera);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            carrera = Optional.of(crearCarrera(resultSet));
        }
        return carrera;
    }
    private Carrera crearCarrera(ResultSet resultSet) throws SQLException {
        int idCarrera = resultSet.getInt("idCarrera");
        String nombreCarrera = resultSet.getString("nombreCarrera");
        EstadoCarrera estadoCarrera = EstadoCarrera.valueOf(resultSet.getString("estadoCarrera"));

        return new Carrera(idCarrera, nombreCarrera, estadoCarrera);
    }
}