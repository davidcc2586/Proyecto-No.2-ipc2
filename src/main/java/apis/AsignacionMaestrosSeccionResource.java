package apis;

import clasesAuxiliares.AsignacionMaestrosSeccionServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.objetosRequest.AsignarMaestroRequest;
import objetos.objetosResponse.PaginaResponse;

import java.sql.SQLException;


@Path("asignacionMaestrosSeccion")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AsignacionMaestrosSeccionResource {

    @POST
    @Path("asignarMaestro")
    public Response asignarMaestro(AsignarMaestroRequest asignacion){
        try {
            AsignacionMaestrosSeccionServicio asignacionMaestrosSeccionServicio = new AsignacionMaestrosSeccionServicio();
            asignacionMaestrosSeccionServicio.asignarMaestro(asignacion);
            return Response.ok().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Combinación existente.\"}").build();
        }
    }

    @GET
    @Path("asignacionesMaestro/{idMaestro}")
    public Response asignacionesMaestro(@PathParam("idMaestro")int idMaestro){
        try {
            AsignacionMaestrosSeccionServicio asignacionMaestrosSeccionServicio = new AsignacionMaestrosSeccionServicio();
            return Response.ok(asignacionMaestrosSeccionServicio.asignacionesMaestro(idMaestro)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

        @GET
        @Path("asignacionesMaestroPaginadas/{idMaestro}")
        public Response asignacionesMaestroPaginadas(@PathParam("idMaestro") int idMaestro, @QueryParam("pagina") @DefaultValue("0") int pagina, @QueryParam("tamano") @DefaultValue("10") int tamano) {
            try {
                AsignacionMaestrosSeccionServicio servicio = new AsignacionMaestrosSeccionServicio();
                PaginaResponse<?> asignaciones = servicio.asignacionesMaestroPaginadas(idMaestro, pagina, tamano);
                return Response.ok(asignaciones).build();
            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
            } catch (SQLException e) {
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
            }
    }


}
