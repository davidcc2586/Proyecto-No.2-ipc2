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
        List<Object> entradas = new ArrayList<>();
        entradas.add(estadoPrestamoLibro.name());

        String sqlConsulta = "SELECT *, titulo AS nombreLibro FROM PrestamoLibro JOIN Libro ON PrestamoLibro.idLibro = Libro.idLibro WHERE estadoPrestamo = ?";

        if (filtroMorosos) {
            sqlConsulta += " AND estadoFinal = ?";
            entradas.add(EstadoFinalPrestamoLibro.MOROSO.name());
        }
        return prestamos(entradas, fechaActual, sqlConsulta);
    }

    public List<PrestamoLibroResponse> prestamosLibroEstudiante(int idEstudiante, LocalDate fechaActual, boolean filtroMorosos, EstadoPrestamoLibro estadoPrestamoLibro) throws SQLException {
        List<Object> entradas = new ArrayList<>();
        entradas.add(estadoPrestamoLibro.name());

        String sqlConsulta = "SELECT *, titulo AS nombreLibro FROM PrestamoLibro JOIN Libro ON PrestamoLibro.idLibro = Libro.idLibro WHERE estadoPrestamo = ?";

        if (filtroMorosos) {
            sqlConsulta += " AND estadoFinal = ?";
            entradas.add(EstadoFinalPrestamoLibro.MOROSO.name());
        }


        sqlConsulta += " AND PrestamoLibro.idEstudiante = ?";
        entradas.add(idEstudiante);

        return prestamos(entradas, fechaActual, sqlConsulta);
    }

    private List<PrestamoLibroResponse> prestamos(List<Object> entradas, LocalDate fechaActual, String sqlConsulta) throws SQLException {
        List<PrestamoLibroResponse> prestamosLibro = new ArrayList<>();
        try (Connection connection = ConnectionDB.getConexion()){
            actualizarPrestamosMorosos(connection, fechaActual);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlConsulta)) {
                for (int i = 0; i < entradas.size(); i++) {
                    preparedStatement.setObject(i + 1, entradas.get(i));
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    prestamosLibro.add(crearPrestamoLibro(resultSet));
                }
            }
            return prestamosLibro;
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
        try (Connection connection =  ConnectionDB.getConexion()){
            try {
                connection.setAutoCommit(false);

                String sql = "INSERT INTO PrestamoLibro(idLibro, idEstudiante, costoDia, fechaPrestamo, fechaEstimadaDevolucion) VALUES (?,?,?,?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                    preparedStatement.setInt(1, Integer.parseInt(prestamo.getIdLibro()));
                    preparedStatement.setInt(2, Integer.parseInt(prestamo.getIdEstudiante()));
                    preparedStatement.setDouble(3,Double.parseDouble(prestamo.getCostoDia()));
                    preparedStatement.setDate(4,java.sql.Date.valueOf(prestamo.getFechaPrestamo()));
                    preparedStatement.setDate(5,java.sql.Date.valueOf(prestamo.getFechaEstimadaDevolucion()));
                    preparedStatement.executeUpdate();
                }

                String sqlRestarLibro = "UPDATE Libro SET cantidad = cantidad -1 WHERE idLibro = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRestarLibro)){
                    preparedStatement.setInt(1, Integer.parseInt(prestamo.getIdLibro()));
                    preparedStatement.executeUpdate();
                }

                connection.commit();
            } catch (Exception e){
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
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

            try {
                connection.setAutoCommit(false);






                String sqlCambiaEstado = "UPDATE PrestamoLibro SET estadoPrestamo = ? WHERE idPrestamoLibro = ?";
                try (PreparedStatement preparedStatement2 = connection.prepareStatement(sqlCambiaEstado)){
                    preparedStatement2.setString(1,EstadoPrestamoLibro.ENTREGADO.name());
                    preparedStatement2.setInt(2,Integer.parseInt(prestamo.getIdPrestamoLibro()));
                    preparedStatement2.executeUpdate();
                }

                String sqlIngresarLibro = "UPDATE Libro libro JOIN PrestamoLibro prestamo ON libro.idLibro = prestamo.idLibro SET libro.cantidad = libro.cantidad + 1 WHERE prestamo.idPrestamoLibro = ?";
                try (PreparedStatement preparedStatement1 = connection.prepareStatement(sqlIngresarLibro)){
                    preparedStatement.setInt(1, Integer.parseInt(prestamo.getIdPrestamoLibro()));
                }
                connection.commit();
            } catch (Exception e){
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
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
