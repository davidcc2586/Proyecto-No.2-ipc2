package objetos.objetosDB;

import objetos.enums.TipoPago;
import objetos.objetosClases.Pago;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagoDB {

    public List<Pago> todosLosPagos() throws SQLException {
        Connection connection = DBConnectionSingleton.getInstance().getConnection();
        List<Pago> pagos = new ArrayList<>();
        String sqlSolicitarPagos = "SELECT * FROM Pago";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarPagos);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                pagos.add(crearPago(resultSet));
            }
        }

        return pagos;
    }

    private Pago crearPago(ResultSet resultSet) throws SQLException{
        int idPago = resultSet.getInt("idPago");
        int idEstudiante = resultSet.getInt("idEstudiante");
        int idSecretario = resultSet.getInt("idSecretario");
        TipoPago tipoPago = TipoPago.valueOf(resultSet.getString("tipoPago"));
        double cantidad = resultSet.getDouble("cantidad");
        String descripcion = resultSet.getString("descripcion");
        LocalDate fecha = resultSet.getDate("fecha").toLocalDate();
        return new Pago(idPago,idEstudiante,idSecretario,tipoPago,cantidad,descripcion,fecha);
    }
}
