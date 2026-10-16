package apis;

import clasesAuxiliares.PagoServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.exceptions.ValidacionException;
import objetos.objetosRequest.PagoRequest;
import objetos.objetosResponse.Solvencia;

import java.sql.SQLException;

@Path("pago")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagoResource {

    @POST
    @Path("registrar")
    public Response registrarPago(PagoRequest pagoRequest){
        try {
            PagoServicio pagoServicio = new PagoServicio();
            pagoServicio.registrarPago(pagoRequest);
            return Response.ok().entity("{\"mensaje\": \"Pago registrado correctamente\"}").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        } catch (ValidacionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("solvenciaEconomica/{idEstudiante}/{anioSolvencia}/{mes}")
    public Response generarSolvenciaEconomica(@PathParam("idEstudiante") int idEstudiante, @PathParam("anioSolvencia") int anioSolvencia, @PathParam("mes") int mes ) {
        try {
            PagoServicio pagoServicio = new PagoServicio();
            Solvencia solvencia = pagoServicio.emisionSolvenciaEconomica(idEstudiante,anioSolvencia,mes);
            return Response.ok(solvencia).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
}
