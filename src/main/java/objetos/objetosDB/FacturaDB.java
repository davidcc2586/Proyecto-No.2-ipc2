package objetos.objetosDB;

import objetos.objetosResponse.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacturaDB {

    public List<Factura> todosLasFacturas() throws SQLException {
        List<Factura> facturaResponses = new ArrayList<>();
        String sqlSolicitarFacturas = "SELECT * FROM Factura ORDER BY idFactura DESC";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarFacturas);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                facturaResponses.add(crearFactura(resultSet));
            }
        }

        return facturaResponses;
    }
    public void ingresarNuevaFactura(Factura factura) throws SQLException{
        String sqlIngresarFactura = "insert into Factura(idPago, nombreConsumidor, NIT) VALUES(?,?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarFactura)){
            preparedStatement.setInt(1, factura.getIdPago());
            preparedStatement.setString(2, factura.getNombreConsumidor());
            preparedStatement.setString(3, factura.getNit());
            preparedStatement.executeUpdate();
        }
    }

    public int ingresarNuevaFactura(Connection connection, int idPago, String nombreConsumidor, String nit) throws SQLException {
        String sql = "INSERT INTO Factura(idPago, nombreConsumidor, NIT) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, idPago);
            preparedStatement.setString(2, nombreConsumidor == null || nombreConsumidor.isBlank() ? "CONSUMIDOR FINAL" : nombreConsumidor.trim());
            preparedStatement.setString(3, nit == null || nit.isBlank() ? "CF" : nit.trim());
            preparedStatement.executeUpdate();
            try (ResultSet clavesGeneradas = preparedStatement.getGeneratedKeys()) {
                if (clavesGeneradas.next()) {
                    return clavesGeneradas.getInt(1);
                }
            }
        }
        throw new SQLException("No se pudo obtener el número de la factura.");
    }

    private Factura crearFactura(ResultSet resultSet) throws SQLException{
        int idFactura = resultSet.getInt("idFactura");
        int idPago = resultSet.getInt("idPago");
        String nombreConsumidor = resultSet.getString("nombreConsumidor");
        String nit = resultSet.getString("NIT");
        return new Factura(idFactura,idPago,nombreConsumidor,nit);
    }
}
