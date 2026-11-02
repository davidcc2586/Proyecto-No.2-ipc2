package objetos.objetosDB;

import objetos.enums.EstadoFinalPrestamoLibro;
import objetos.enums.EstadoPrestamoLibro;
import objetos.objetosRequest.PrestamoLibroDevolucionRequest;
import objetos.objetosRequest.PrestamoLibroRequest;
import objetos.exceptions.ValidacionException;
import objetos.objetosResponse.PrestamoLibroResponse;
import objetos.objetosResponse.PaginaResponse;

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

    public PaginaResponse<PrestamoLibroResponse> prestamosLibroPaginados(LocalDate fechaActual, boolean filtroMorosos, EstadoPrestamoLibro estadoPrestamoLibro, int pagina, int tamano) throws SQLException {
        List<Object> entradas = new ArrayList<>();
        entradas.add(estadoPrestamoLibro.name());
        String sqlConsulta = "SELECT *, titulo AS nombreLibro FROM PrestamoLibro JOIN Libro ON PrestamoLibro.idLibro = Libro.idLibro WHERE estadoPrestamo = ?";
        if (filtroMorosos) {
            sqlConsulta += " AND estadoFinal = ?";
            entradas.add(EstadoFinalPrestamoLibro.MOROSO.name());
        }
        return prestamosPaginados(entradas, fechaActual, sqlConsulta, pagina, tamano);
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

    public PaginaResponse<PrestamoLibroResponse> prestamosLibroEstudiantePaginados(int idEstudiante, LocalDate fechaActual, boolean filtroMorosos, EstadoPrestamoLibro estadoPrestamoLibro, int pagina, int tamano) throws SQLException {
        List<Object> entradas = new ArrayList<>();
        entradas.add(estadoPrestamoLibro.name());
        String sqlConsulta = "SELECT *, titulo AS nombreLibro FROM PrestamoLibro JOIN Libro ON PrestamoLibro.idLibro = Libro.idLibro WHERE estadoPrestamo = ?";
        if (filtroMorosos) {
            sqlConsulta += " AND estadoFinal = ?";
            entradas.add(EstadoFinalPrestamoLibro.MOROSO.name());
        }
        sqlConsulta += " AND PrestamoLibro.idEstudiante = ?";
        entradas.add(idEstudiante);
        return prestamosPaginados(entradas, fechaActual, sqlConsulta, pagina, tamano);
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
        }
        return prestamosLibro;
    }

    private PaginaResponse<PrestamoLibroResponse> prestamosPaginados(List<Object> entradas, LocalDate fechaActual, String sqlConsulta, int pagina, int tamano) throws SQLException {
        int offset = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<PrestamoLibroResponse> prestamos = new ArrayList<>();
        int totalElementos = 0;
        String sqlConteo = sqlConsulta.replace("SELECT *, titulo AS nombreLibro", "SELECT COUNT(*)");

        try (Connection connection = ConnectionDB.getConexion()) {
            actualizarPrestamosMorosos(connection, fechaActual);
            try (PreparedStatement conteo = connection.prepareStatement(sqlConteo)) {
                for (int i = 0; i < entradas.size(); i++) {
                    conteo.setObject(i + 1, entradas.get(i));
                }
                try (ResultSet resultado = conteo.executeQuery()) {
                    if (resultado.next()) {
                        totalElementos = resultado.getInt(1);
                    }
                }
            }

            String sqlPaginado = sqlConsulta + " ORDER BY PrestamoLibro.idPrestamoLibro LIMIT ? OFFSET ?";
            try (PreparedStatement consulta = connection.prepareStatement(sqlPaginado)) {
                for (int i = 0; i < entradas.size(); i++) {
                    consulta.setObject(i + 1, entradas.get(i));
                }
                consulta.setInt(entradas.size() + 1, tamano);
                consulta.setInt(entradas.size() + 2, offset);
                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        prestamos.add(crearPrestamoLibro(resultado));
                    }
                }
            }
        }
        return new PaginaResponse<>(prestamos, pagina, tamano, totalElementos);
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

            String sqlActualizarMulta = "UPDATE PrestamoLibro SET multa = (DATEDIFF(?,fechaEstimadaDevolucion) * costoDia) WHERE estadoFinal = ? AND estadoPrestamo = ?";
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

    public void registrarNuevoPrestamoLibro(PrestamoLibroRequest prestamo) throws SQLException, ValidacionException {
        try (Connection connection =  ConnectionDB.getConexion()){
            try {
                connection.setAutoCommit(false);

                String sqlRestarLibro = "UPDATE Libro SET cantidad = cantidad - 1 WHERE idLibro = ? AND estado = 'ACTIVO' AND cantidad > 0";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRestarLibro)){
                    preparedStatement.setInt(1, Integer.parseInt(prestamo.getIdLibro()));
                    if (preparedStatement.executeUpdate() == 0) {
                        throw new ValidacionException("No hay existencias disponibles para prestar ese libro.");
                    }
                }

                String sql = "INSERT INTO PrestamoLibro(idLibro, idEstudiante, costoDia, fechaPrestamo, fechaEstimadaDevolucion) VALUES (?,?,?,?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                    preparedStatement.setInt(1, Integer.parseInt(prestamo.getIdLibro()));
                    preparedStatement.setInt(2, Integer.parseInt(prestamo.getIdEstudiante()));
                    preparedStatement.setDouble(3,Double.parseDouble(prestamo.getCostoDia()));
                    preparedStatement.setDate(4,java.sql.Date.valueOf(prestamo.getFechaPrestamo()));
                    preparedStatement.setDate(5,java.sql.Date.valueOf(prestamo.getFechaEstimadaDevolucion()));
                    preparedStatement.executeUpdate();
                }

                connection.commit();
            } catch (SQLException | RuntimeException e){
                connection.rollback();
                throw e;
            } catch (ValidacionException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    public void registrarDevolucionLibro(PrestamoLibroDevolucionRequest prestamo) throws SQLException {
        try (Connection connection = ConnectionDB.getConexion()) {

            LocalDate fechaDev = null;
            if (prestamo.getFechaDevolucion() != null) {
                fechaDev = LocalDate.parse(prestamo.getFechaDevolucion());
            }
            try {
                connection.setAutoCommit(false);

                String sqlActualizarPrestamo = "UPDATE PrestamoLibro SET estadoPrestamo = ?, fechaDevolucion = ?, estadoFinal = IF(? > fechaEstimadaDevolucion, ?, ?), multa = GREATEST(DATEDIFF(?, fechaEstimadaDevolucion), 0) * costoDia WHERE idPrestamoLibro = ? AND estadoPrestamo = ?";
                try (PreparedStatement psPrestamo = connection.prepareStatement(sqlActualizarPrestamo)) {
                    psPrestamo.setString(1, EstadoPrestamoLibro.ENTREGADO.name());

                    if (fechaDev != null) {
                        psPrestamo.setDate(2, java.sql.Date.valueOf(fechaDev));
                    } else {
                        psPrestamo.setNull(2, java.sql.Types.DATE);
                    }

                    if (fechaDev != null) {
                        psPrestamo.setDate(3, java.sql.Date.valueOf(fechaDev));
                    } else {
                        psPrestamo.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                    }
                    psPrestamo.setString(4, EstadoFinalPrestamoLibro.MOROSO.name());
                    psPrestamo.setString(5, EstadoFinalPrestamoLibro.PUNTUAL.name());
                    if (fechaDev != null) {
                        psPrestamo.setDate(6, java.sql.Date.valueOf(fechaDev));
                    } else {
                        psPrestamo.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
                    }
                    psPrestamo.setInt(7, Integer.parseInt(prestamo.getIdPrestamoLibro()));
                    psPrestamo.setString(8, EstadoPrestamoLibro.PRESTADO.name());
                    if (psPrestamo.executeUpdate() == 0) {
                        throw new SQLException("No se encontró un préstamo pendiente para registrar la devolución.");
                    }
                }

                String sqlIngresarLibro = "UPDATE Libro libro JOIN PrestamoLibro prestamo ON libro.idLibro = prestamo.idLibro SET libro.cantidad = libro.cantidad + 1 WHERE prestamo.idPrestamoLibro = ?";
                try (PreparedStatement psLibro = connection.prepareStatement(sqlIngresarLibro)) {
                    psLibro.setInt(1, Integer.parseInt(prestamo.getIdPrestamoLibro()));
                    if (psLibro.executeUpdate() == 0) {
                        throw new SQLException("No se pudo actualizar el inventario del libro devuelto.");
                    }
                }

                connection.commit();
            } catch (Exception e) {
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

        LocalDate fechaPrestamo = null;
        if (resultSet.getDate("fechaPrestamo") != null) {
            fechaPrestamo = resultSet.getDate("fechaPrestamo").toLocalDate();
        }

        LocalDate fechaEstimadaDevolucion = null;
        if (resultSet.getDate("fechaEstimadaDevolucion") != null) {
            fechaEstimadaDevolucion = resultSet.getDate("fechaEstimadaDevolucion").toLocalDate();
        }

        LocalDate fechaDevolucion = null;
        if (resultSet.getDate("fechaDevolucion") != null) {
            fechaDevolucion = resultSet.getDate("fechaDevolucion").toLocalDate();
        }

        double multa = resultSet.getDouble("multa");
        String nombreLIbro = resultSet.getString("nombreLibro");

        return new PrestamoLibroResponse( idPrestamoLibro, idLibro, nombreLIbro, idEstudiante, estadoPrestamoLibro, estadoFinalPrestamoLibro, costoDia, fechaPrestamo, fechaEstimadaDevolucion, fechaDevolucion, multa);
    }
}
