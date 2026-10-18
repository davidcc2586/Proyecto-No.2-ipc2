package apis;

import clasesAuxiliares.AsignacionMaestrosSeccionServicio;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.objetosRequest.AsignarMaestroRequest;

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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }
}
