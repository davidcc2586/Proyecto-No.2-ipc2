package clasesAuxiliares;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import objetos.objetosDB.ConnectionDB;
import objetos.objetosDB.ConfiguracionColegioDB;
import objetos.objetosResponse.ReporteIngresoFila;
import objetos.objetosResponse.ReporteMoraBibliotecaFila;
import objetos.objetosResponse.ReportePagoFila;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReportesServicio {
    private static final Map<String, JasperReport> REPORTS = new ConcurrentHashMap<>();

    public byte[] historialPagos(int idEstudiante, String fechaInicio, String fechaFin)
        throws SQLException, JRException, IOException {
        LocalDate inicio = parsearFecha(fechaInicio, "fechaInicio");
        LocalDate fin = parsearFecha(fechaFin, "fechaFin");
        validarRango(inicio, fin);

        List<ReportePagoFila> filas = new ArrayList<>();
        String nombreEstudiante;
        String sqlEstudiante = "SELECT CONCAT(nombre, ' ', apellido) AS nombre FROM Estudiante WHERE idEstudiante = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement statement = connection.prepareStatement(sqlEstudiante)) {
            statement.setInt(1, idEstudiante);
            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    throw new IllegalArgumentException("No se encontró el estudiante solicitado.");
                }
                nombreEstudiante = result.getString("nombre");
            }
        }

        StringBuilder sql = new StringBuilder(
            "SELECT p.fecha, p.tipoPago, p.cantidad, p.descripcion, f.idFactura "
                + "FROM Pago p LEFT JOIN Factura f ON f.idPago = p.idPago "
                + "WHERE p.idEstudiante = ?"
        );
        List<Object> parametros = new ArrayList<>();
        parametros.add(idEstudiante);
        if (inicio != null) {
            sql.append(" AND p.fecha >= ?");
            parametros.add(java.sql.Date.valueOf(inicio));
        }
        if (fin != null) {
            sql.append(" AND p.fecha <= ?");
            parametros.add(java.sql.Date.valueOf(fin));
        }
        sql.append(" ORDER BY p.fecha DESC, p.idPago DESC");
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            asignarParametros(statement, parametros);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int idFactura = result.getInt("idFactura");
                    boolean facturaNula = result.wasNull();
                    filas.add(new ReportePagoFila(
                        result.getDate("fecha").toLocalDate().toString(),
                        result.getString("tipoPago"),
                        result.getDouble("cantidad"),
                        result.getString("descripcion"),
                        facturaNula ? "—" : "#" + idFactura
                    ));
                }
            }
        }

        List<Integer> mesesPendientes = mesesPendientes(idEstudiante);
        Map<String, Object> parametrosReporte = new HashMap<>();
        parametrosReporte.put("nombreEstudiante", nombreEstudiante);
        parametrosReporte.put("rangoFechas", textoRango(inicio, fin));
        parametrosReporte.put("totalPagado", filas.stream().mapToDouble(ReportePagoFila::getCantidad).sum());
        parametrosReporte.put("mesesPendientes", mesesPendientes.isEmpty()
            ? "Ninguno"
            : mesesPendientes.stream().map(ReportesServicio::nombreMes).reduce((a, b) -> a + ", " + b).orElse(""));
        return exportar("reportes/historial-pagos.jrxml", filas, parametrosReporte);
    }

    public byte[] ingresos(String fechaInicio, String fechaFin) throws SQLException, JRException, IOException {
        LocalDate inicio = parsearFecha(fechaInicio, "fechaInicio");
        LocalDate fin = parsearFecha(fechaFin, "fechaFin");
        validarRango(inicio, fin);
        StringBuilder sql = new StringBuilder("SELECT tipoPago, COUNT(*) AS cantidadPagos, COALESCE(SUM(cantidad), 0) AS total FROM Pago WHERE 1 = 1");
        List<Object> parametros = new ArrayList<>();
        if (inicio != null) {
            sql.append(" AND fecha >= ?");
            parametros.add(java.sql.Date.valueOf(inicio));
        }
        if (fin != null) {
            sql.append(" AND fecha <= ?");
            parametros.add(java.sql.Date.valueOf(fin));
        }
        sql.append(" GROUP BY tipoPago ORDER BY tipoPago");

        List<ReporteIngresoFila> filas = new ArrayList<>();
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            asignarParametros(statement, parametros);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    filas.add(new ReporteIngresoFila(
                        result.getString("tipoPago"),
                        result.getInt("cantidadPagos"),
                        result.getDouble("total")
                    ));
                }
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("rangoFechas", textoRango(inicio, fin));
        params.put("totalIngresos", filas.stream().mapToDouble(ReporteIngresoFila::getTotal).sum());
        params.put("cantidadPagos", filas.stream().mapToInt(ReporteIngresoFila::getCantidadPagos).sum());
        return exportar("reportes/ingresos.jrxml", filas, params);
    }

    public byte[] morosidadBiblioteca() throws SQLException, JRException, IOException {
        List<ReporteMoraBibliotecaFila> filas = new ArrayList<>();
        String sql = "SELECT l.titulo, CONCAT(e.nombre, ' ', e.apellido) AS estudiante, e.cui, "
            + "p.fechaEstimadaDevolucion, DATEDIFF(CURDATE(), p.fechaEstimadaDevolucion) AS diasRetraso, "
            + "p.costoDia, DATEDIFF(CURDATE(), p.fechaEstimadaDevolucion) * p.costoDia AS recargo "
            + "FROM PrestamoLibro p JOIN Libro l ON l.idLibro = p.idLibro "
            + "JOIN Estudiante e ON e.idEstudiante = p.idEstudiante "
            + "WHERE p.estadoPrestamo = 'PRESTADO' AND p.fechaEstimadaDevolucion < CURDATE() "
            + "ORDER BY diasRetraso DESC, e.apellido, e.nombre";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                filas.add(new ReporteMoraBibliotecaFila(
                    result.getString("titulo"),
                    result.getString("estudiante"),
                    result.getString("cui"),
                    result.getDate("fechaEstimadaDevolucion").toLocalDate().toString(),
                    result.getInt("diasRetraso"),
                    result.getDouble("costoDia"),
                    result.getDouble("recargo")
                ));
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("fechaGeneracion", LocalDate.now().toString());
        params.put("totalRecargos", filas.stream().mapToDouble(ReporteMoraBibliotecaFila::getRecargo).sum());
        return exportar("reportes/morosidad-biblioteca.jrxml", filas, params);
    }

    private List<Integer> mesesPendientes(int idEstudiante) throws SQLException {
        int anioLectivo = new ConfiguracionColegioDB().obtenerAñoLectivoActual();
        if (anioLectivo == 0) {
            return List.of();
        }
        List<Integer> pagados = new ArrayList<>();
        String sql = "SELECT DISTINCT mes FROM DetallePagoColegiatura WHERE idEstudiante = ? AND anio = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idEstudiante);
            statement.setInt(2, anioLectivo);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    pagados.add(result.getInt(1));
                }
            }
        }
        int mesActual = anioLectivo < LocalDate.now().getYear()
            ? 12
            : anioLectivo == LocalDate.now().getYear() ? LocalDate.now().getMonthValue() : 0;
        List<Integer> pendientes = new ArrayList<>();
        for (int mes = 1; mes <= mesActual; mes++) {
            if (!pagados.contains(mes)) {
                pendientes.add(mes);
            }
        }
        return pendientes;
    }

    private byte[] exportar(String template, List<?> filas, Map<String, Object> parametros)
        throws JRException, IOException {
        JasperReport report = REPORTS.get(template);
        if (report == null) {
            try (InputStream input = getClass().getClassLoader().getResourceAsStream(template)) {
                if (input == null) {
                    throw new JRException("No se encontró la plantilla JasperReports: " + template);
                }
                JasperReport compilado = JasperCompileManager.compileReport(input);
                JasperReport existente = REPORTS.putIfAbsent(template, compilado);
                report = existente == null ? compilado : existente;
            }
        }
        JasperPrint print = JasperFillManager.fillReport(
            report,
            parametros,
            new JRBeanCollectionDataSource(filas)
        );
        return JasperExportManager.exportReportToPdf(print);
    }

    private void asignarParametros(PreparedStatement statement, List<Object> parametros) throws SQLException {
        for (int i = 0; i < parametros.size(); i++) {
            statement.setObject(i + 1, parametros.get(i));
        }
    }

    private LocalDate parsearFecha(String fecha, String nombreParametro) {
        if (fecha == null || fecha.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(fecha);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("El parámetro " + nombreParametro + " debe tener formato yyyy-MM-dd.");
        }
    }

    private void validarRango(LocalDate inicio, LocalDate fin) {
        if (inicio != null && fin != null && inicio.isAfter(fin)) {
            throw new IllegalArgumentException("La fecha inicial no puede ser posterior a la fecha final.");
        }
    }

    private String textoRango(LocalDate inicio, LocalDate fin) {
        if (inicio == null && fin == null) return "Histórico completo";
        return (inicio == null ? "Inicio abierto" : inicio) + " a " + (fin == null ? "actualidad" : fin);
    }

    private static String nombreMes(int mes) {
        return switch (mes) {
            case 1 -> "Enero";
            case 2 -> "Febrero";
            case 3 -> "Marzo";
            case 4 -> "Abril";
            case 5 -> "Mayo";
            case 6 -> "Junio";
            case 7 -> "Julio";
            case 8 -> "Agosto";
            case 9 -> "Septiembre";
            case 10 -> "Octubre";
            case 11 -> "Noviembre";
            case 12 -> "Diciembre";
            default -> String.valueOf(mes);
        };
    }
}
