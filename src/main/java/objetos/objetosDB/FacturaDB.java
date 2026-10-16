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
        String sqlSolicitarFacturas = "SELECT * FROM Factura";

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
    private Factura crearFactura(ResultSet resultSet) throws SQLException{
        int idFactura = resultSet.getInt("idFactura");
        int idPago = resultSet.getInt("idPago");
        String nombreConsumidor = resultSet.getString("nombreConsumidor");
        String nit = resultSet.getString("NIT");
        return new Factura(idFactura,idPago,nombreConsumidor,nit);
    }
}
