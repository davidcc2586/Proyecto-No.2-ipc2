package objetos.objetosDB;

import objetos.objetosClases.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacturaDB {

    public List<Factura> todosLasFacturas() throws SQLException {
        Connection connection = DBConnectionSingleton.getInstance().getConnection();
        List<Factura> facturas = new ArrayList<>();
        String sqlSolicitarFacturas = "SELECT * FROM Factura";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarFacturas);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                facturas.add(crearFactura(resultSet));
            }
        }

        return facturas;
    }

    private Factura crearFactura(ResultSet resultSet) throws SQLException{
        int idFactura = resultSet.getInt("idFactura");
        int idPago = resultSet.getInt("idPago");
        String nombreConsumidor = resultSet.getString("nombreConsumidor");
        String nit = resultSet.getString("NIT");
        return new Factura(idFactura,idPago,nombreConsumidor,nit);
    }
}
