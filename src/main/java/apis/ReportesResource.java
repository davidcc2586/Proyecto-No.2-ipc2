package apis;

import clasesAuxiliares.ReportesServicio;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("reportes")
public class ReportesResource {
    private static final Logger LOGGER = Logger.getLogger(ReportesResource.class.getName());
    private final ReportesServicio reportesServicio = new ReportesServicio();

    @GET
    @Path("pagos/{idEstudiante}")
    @Produces("application/pdf")
    public Response historialPagos(@PathParam("idEstudiante") int idEstudiante, @QueryParam("fechaInicio") String fechaInicio, @QueryParam("fechaFin") String fechaFin) {
        try {
            byte[] pdf = reportesServicio.historialPagos(idEstudiante, fechaInicio, fechaFin);
            return pdf(pdf, "historial-pagos.pdf");
        } catch (IllegalArgumentException e) {
            return error(Response.Status.BAD_REQUEST, e.getMessage());
        } catch (SQLException | JRException | IOException e) {
            return errorInterno("No se pudo generar el historial de pagos.", e);
        }
    }

    @GET
    @Path("ingresos")
    @Produces("application/pdf")
    public Response ingresos(@QueryParam("fechaInicio") String fechaInicio, @QueryParam("fechaFin") String fechaFin
    ) {
        try {
            byte[] pdf = reportesServicio.ingresos(fechaInicio, fechaFin);
            return pdf(pdf, "reporte-ingresos.pdf");
        } catch (IllegalArgumentException e) {
            return error(Response.Status.BAD_REQUEST, e.getMessage());
        } catch (SQLException | JRException | IOException e) {
            return errorInterno("No se pudo generar el reporte de ingresos.", e);
        }
    }

    @GET
    @Path("biblioteca/morosidad")
    @Produces("application/pdf")
    public Response morosidadBiblioteca() {
        try {
            byte[] pdf = reportesServicio.morosidadBiblioteca();
            return pdf(pdf, "morosidad-biblioteca.pdf");
        } catch (SQLException | JRException | IOException e) {
            return errorInterno("No se pudo generar el reporte de morosidad de biblioteca.", e);
        }
    }

    private Response pdf(byte[] contenido, String nombreArchivo) {
        return Response.ok(contenido, "application/pdf").header("Content-Disposition", "inline; filename=\"" + nombreArchivo + "\"").build();
    }

    private Response error(Response.Status status, String mensaje) {
        return Response.status(status).type("application/json").entity("{\"error\":\"" + escaparJson(mensaje) + "\"}").build();
    }

    private Response errorInterno(String mensaje, Exception exception) {
        LOGGER.log(Level.SEVERE, mensaje, exception);
        return error(Response.Status.INTERNAL_SERVER_ERROR, mensaje);
    }

    private String escaparJson(String texto) {
        return texto.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
