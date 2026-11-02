package objetos.objetosDB;

import objetos.enums.TipoPago;
import objetos.objetosRequest.PagoRequest;
import objetos.objetosResponse.Pago;
import objetos.objetosResponse.EstadoCuentaResponse;

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
    public int ingresarNuevoPago(PagoRequest pago) throws SQLException {
        try (Connection connection = ConnectionDB.getConexion()) {
            connection.setAutoCommit(false);
            try {
                int idFactura = guardarPago(connection, pago);
                connection.commit();
                return idFactura;
            } catch (SQLException | RuntimeException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    public EstadoCuentaResponse estadoCuenta(int idEstudiante) throws SQLException {
        int anioLectivo = new ConfiguracionColegioDB().obtenerAñoLectivoActual();
        List<Pago> pagos = new ArrayList<>();
        String sqlPagos = "SELECT * FROM Pago WHERE idEstudiante = ? ORDER BY fecha DESC, idPago DESC";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlPagos)) {
            preparedStatement.setInt(1, idEstudiante);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    pagos.add(crearPago(resultSet));
                }
            }
        }

        List<Integer> mesesPagados = new ArrayList<>();
        if (anioLectivo > 0) {
            String sqlMeses = "SELECT DISTINCT mes FROM DetallePagoColegiatura WHERE idEstudiante = ? AND anio = ?";
            try (Connection connection = ConnectionDB.getConexion();
                 PreparedStatement preparedStatement = connection.prepareStatement(sqlMeses)) {
                preparedStatement.setInt(1, idEstudiante);
                preparedStatement.setInt(2, anioLectivo);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        mesesPagados.add(resultSet.getInt("mes"));
                    }
                }
            }
        }

        int anioActual = LocalDate.now().getYear();
        int mesesVencidosHasta;

        if (anioLectivo == 0 || anioLectivo > anioActual) {
            mesesVencidosHasta = 0;
        } else if (anioLectivo < anioActual) {
            mesesVencidosHasta = 12;
        } else {
            mesesVencidosHasta = LocalDate.now().getMonthValue();
        }

        List<Integer> mesesPendientes = new ArrayList<>();
        for (int mes = 1; mes <= mesesVencidosHasta; mes++) {
            if (!mesesPagados.contains(mes)) {
                mesesPendientes.add(mes);
            }
        }
        return new EstadoCuentaResponse(anioLectivo, pagos, mesesPendientes);
    }

    private int guardarPago(Connection connection, PagoRequest pago) throws SQLException {
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

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (!resultSet.next()) {
                    throw new SQLException("No se pudo obtener el identificador del pago.");
                }
                idPagoGenerado = resultSet.getInt(1);
            }
        }

        if (pago.getTipoPago().equalsIgnoreCase(TipoPago.COLEGIATURA.name())) {
            String sqlIngresarDetallesPagoColegiatura = "INSERT INTO DetallePagoColegiatura(idpago, idestudiante, anio, mes) VALUES (?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarDetallesPagoColegiatura)) {
                preparedStatement.setInt(1, idPagoGenerado);
                preparedStatement.setInt(2, Integer.parseInt(pago.getIdEstudiante()));
                preparedStatement.setInt(3, fechaPago.getYear());
                preparedStatement.setInt(4, fechaPago.getMonthValue());
                preparedStatement.executeUpdate();
            }
        }

        return new FacturaDB().ingresarNuevaFactura(
            connection,
            idPagoGenerado,
            pago.getNombreConsumidor(),
            pago.getNit()
        );
    }

    public int ultimoMesPagado(int idEstudiante, int anioLectivoActual) throws SQLException{
        String sql = "SELECT MAX(mes) FROM DetallePagoColegiatura WHERE idEstudiante = ? AND anio = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idEstudiante);
            preparedStatement.setInt(2, anioLectivoActual);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int mes = resultSet.getInt(1);
                    if (mes == 0) {
                        return 1;
                    }
                    return mes;
                }
            }
        }
        return -1;
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
