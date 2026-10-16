package objetos.objetosDB;

import objetos.enums.EstadoFinalPrestamoLibro;
import objetos.enums.EstadoPrestamoLibro;
import objetos.objetosRequest.PrestamoLibroDevolucionRequest;
import objetos.objetosRequest.PrestamoLibroRequest;
import objetos.objetosResponse.PrestamoLibroResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoLibroDB {

    public List<PrestamoLibroResponse> prestamosLibro(LocalDate fechaActual, boolean filtroMorosos, EstadoPrestamoLibro estadoPrestamoLibro) throws SQLException {
        try (Connection connection = ConnectionDB.getConexion()){
            List<PrestamoLibroResponse> prestamosLIbro = new ArrayList<>();
            actualizarPrestamosMorosos(connection, fechaActual);

            String sqlConsulta = "SELECT *, titulo AS nombreLibro FROM PrestamoLibro JOIN Libro ON PrestamoLibro.idLibro = Libro.idLibro WHERE estadoPrestamo =  ?";
            if (filtroMorosos) {
                sqlConsulta += "AND estadoFinal = ? ";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlConsulta)) {
                    preparedStatement.setString(1, estadoPrestamoLibro.name());
                    preparedStatement.setString(2, EstadoFinalPrestamoLibro.MOROSO.name());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        prestamosLIbro.add(crearPrestamoLibro(resultSet));
                    }
                }
            } else {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlConsulta)) {
                    preparedStatement.setString(1, estadoPrestamoLibro.name());
                     ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        prestamosLIbro.add(crearPrestamoLibro(resultSet));
                    }
                }
            }
            return prestamosLIbro;
        }
    }

    public List<PrestamoLibroResponse> consultarPrestamoLibroEstudiante(int idEstudiante, boolean filtroMorosos, LocalDate fechaActual) throws SQLException{
        try(Connection connection = ConnectionDB.getConexion()){
            List<PrestamoLibroResponse> prestamosLIbro = new ArrayList<>();

            actualizarPrestamosMorosos(connection, fechaActual);

            if (filtroMorosos) {
                String sqlSolicitarPrestamosLibro = "SELECT * FROM PrestamoLibro WHERE estadoFinal = ? AND estadoPrestamo AND idEstudiante = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarPrestamosLibro)) {
                    preparedStatement.setString(1, EstadoFinalPrestamoLibro.MOROSO.name());
                    preparedStatement.setString(2, EstadoPrestamoLibro.PRESTADO.name());
                    preparedStatement.setInt(3, idEstudiante);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        prestamosLIbro.add(crearPrestamoLibro(resultSet));
                    }
                }
            } else {
                String sqlSolicitarPrestamosLibro = "SELECT * FROM PrestamoLibro WHERE idEstudiante = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarPrestamosLibro)) {
                    preparedStatement.setInt(1, idEstudiante);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        prestamosLIbro.add(crearPrestamoLibro(resultSet));
                    }
                }
            }

            return prestamosLIbro;
        }
    }

    public void cambiaEstadoPrestamoLibro(int idPrestamo, EstadoPrestamoLibro estadoPrestamoLibro) throws SQLException{
        String sqlCambiaEstado = "UPDATE PrestamoLibro SET estadoPrestamo = ? WHERE idPrestamoLibro = ?";
        try (Connection connection = ConnectionDB.getConexion();
              PreparedStatement preparedStatement = connection.prepareStatement(sqlCambiaEstado)){
            preparedStatement.setString(1,estadoPrestamoLibro.name());
            preparedStatement.setInt(2,idPrestamo);
            preparedStatement.executeUpdate();
        }
    }

    private void actualizarPrestamosMorosos(Connection connection, LocalDate fechaActual) throws SQLException{
        try {
            connection.setAutoCommit(false);

            String sqlActualizarEstados = "UPDATE PrestamoLibro SET estadoFinal = ? WHERE fechaEstimadaDevolucion < ? AND estadoPrestamo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlActualizarEstados)) {
                preparedStatement.setString(1, EstadoFinalPrestamoLibro.MOROSO.name());
                preparedStatement.setDate(2, java.sql.Date.valueOf(fechaActual));
                preparedStatement.setString(3,EstadoPrestamoLibro.PRESTADO.name());
                preparedStatement.executeUpdate();
            }

            String sqlActualizarMulta = "UPDATE PrestamoLibro SET multa = (DATEDIFF(?,fechaEstimadaDevolucion) * 5)  WHERE estadoFinal = ? AND estadoPrestamo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlActualizarMulta)) {
                preparedStatement.setDate(1,java.sql.Date.valueOf(fechaActual));
                preparedStatement.setString(2,EstadoFinalPrestamoLibro.MOROSO.name());
                preparedStatement.setString(3,EstadoPrestamoLibro.PRESTADO.name());
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void registrarNuevoPrestamoLibro(PrestamoLibroRequest prestamo) throws SQLException{
        String sql = "INSERT INTO PrestamoLibro(idLibro, idEstudiante, costoDia, fechaPrestamo, fechaEstimadaDevolucion) VALUES (?,?,?,?,?)";
        try (Connection connection =  ConnectionDB.getConexion()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(prestamo.getIdLibro()));
            preparedStatement.setInt(2, Integer.parseInt(prestamo.getIdEstudiante()));
            preparedStatement.setDouble(3,Double.parseDouble(prestamo.getCostoDia()));
            preparedStatement.setDate(4,java.sql.Date.valueOf(prestamo.getFechaPrestamo()));
            preparedStatement.setDate(5,java.sql.Date.valueOf(prestamo.getFechaEstimadaDevolucion()));
            preparedStatement.executeUpdate();
        }
    }

    public void registrarDevolucionLibro(PrestamoLibroDevolucionRequest prestamo) throws SQLException {
        try (Connection connection = ConnectionDB.getConexion()){
         actualizarPrestamosMorosos(connection,LocalDate.parse(prestamo.getFechaDevolucion()));
         String sql = "UPDATE PrestamoLibro SET estadoPrestamo = ?, fechaDevolucion = ? WHERE idPrestamoLibro = ?";
         PreparedStatement preparedStatement = connection.prepareStatement(sql);

         /*METODO INCOMPLETO,  MONTO DE MULTA YA tiene que
         ESTAR CARGADO A ESTE PUNTO PARA INDICAR CUANTO TIENE QUE
         PAGAR DE MULTA O  PUEDE SER QUE aquí SE LE DIGA EL monto A PAGAR como respuesta
          */
        }
    }

    private PrestamoLibroResponse crearPrestamoLibro(ResultSet resultSet) throws SQLException{
        int idPrestamoLibro = resultSet.getInt("idPrestamoLibro");
        int idLibro = resultSet.getInt("idLibro");
        int idEstudiante = resultSet.getInt("idEstudiante");
        EstadoPrestamoLibro estadoPrestamoLibro = EstadoPrestamoLibro.valueOf(resultSet.getString("estadoPrestamo"));
        EstadoFinalPrestamoLibro estadoFinalPrestamoLibro = EstadoFinalPrestamoLibro.valueOf(resultSet.getString("estadoFinal"));
        double costoDia = resultSet.getDouble("costoDia");
        LocalDate fechaPrestamo = resultSet.getDate("fechaPrestamo").toLocalDate();
        LocalDate fechaEstimadaDevolucion = resultSet.getDate("fechaEstimadaDevolucion").toLocalDate();
        LocalDate fechaDevolucion = resultSet.getDate("fechaDevolucion").toLocalDate();
        double multa = resultSet.getDouble("multa");
        String nombreLIbro = resultSet.getString("nombreLibro");
        return new PrestamoLibroResponse(idPrestamoLibro,idLibro,nombreLIbro,idEstudiante,estadoPrestamoLibro,estadoFinalPrestamoLibro,costoDia,fechaPrestamo,fechaEstimadaDevolucion,fechaDevolucion,multa);
    }
}
