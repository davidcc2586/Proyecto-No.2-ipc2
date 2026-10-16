package objetos.objetosDB;

import objetos.enums.TipoPago;
import objetos.objetosRequest.PagoRequest;
import objetos.objetosResponse.Pago;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagoDB {

    public List<Pago> todosLosPagos() throws SQLException {
        List<Pago> pagoResponses = new ArrayList<>();
        String sqlSolicitarPagos = "SELECT * FROM Pago";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarPagos);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                pagoResponses.add(crearPago(resultSet));
            }
        }
        return pagoResponses;
    }
    public void ingresarNuevoPago(PagoRequest pago) throws SQLException {
        Connection connection = ConnectionDB.getConexion();
        try {
            connection.setAutoCommit(false);
            LocalDate fechaPago = LocalDate.parse(pago.getFecha());

            String sqlIngresarPago = "INSERT INTO Pago(idestudiante, idsecretario, tipopago, cantidad, descripcion, fecha) VALUES (?,?,?,?,?,?)";
            int idPagoGenerado;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarPago, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, Integer.parseInt(pago.getIdEstudiante()));
                preparedStatement.setInt(2, Integer.parseInt(pago.getIdSecretario()));
                preparedStatement.setString(3, pago.getTipoPago());
                preparedStatement.setDouble(4, Double.parseDouble(pago.getCantidad()));
                preparedStatement.setString(5, pago.getDescripcion());
                preparedStatement.setDate(6, java.sql.Date.valueOf(fechaPago));
                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    idPagoGenerado = resultSet.getInt(1);
                } else {
                    throw new SQLException();
                }

            }
            if (pago.getTipoPago().equalsIgnoreCase(TipoPago.COLEGIATURA.name())) {
                String sqlIngresarDetallesPagoColegiatura = "INSERT INTO DetallePagoColegiatura(idpago, idestudiante, anio, mes) VALUES (?,?,?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarDetallesPagoColegiatura)) {
                    preparedStatement.setInt(1, idPagoGenerado);
                    preparedStatement.setInt(2,Integer.parseInt(pago.getIdEstudiante()));
                    preparedStatement.setInt(3, fechaPago.getYear());
                    preparedStatement.setInt(4, fechaPago.getMonthValue());
                    preparedStatement.executeUpdate();
                }
            }

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    public int ultimoMesPagado(int idEstudiante, int anioLectivoActual) throws SQLException{
        String sqlSolicitarUltimoAñoPagado = "SELECT mes FROM DetallePagoColegiatura WHERE idEstudiante = ? AND anio = ? ORDER BY idDetalle DESC limit 1";
        try (Connection connection = ConnectionDB.getConexion()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarUltimoAñoPagado);
            preparedStatement.setInt(1,idEstudiante);
            preparedStatement.setInt(2,anioLectivoActual);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("mes");
            } else {
                return -1;
            }
        }
    }

    public int cantidadPagosRealizados(int idEstudiante, int anioLectivoActual) throws SQLException{
        String sql = "SELECT COUNT(*) FROM DetallePagoColegiatura WHERE idEstudiante = ? AND anio = ?";
        try (Connection connection = ConnectionDB.getConexion()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,idEstudiante);
            preparedStatement.setInt(2,anioLectivoActual);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            } else {
                throw new SQLException("Estudiante no encontrado con el id " + idEstudiante);
            }
        }
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
