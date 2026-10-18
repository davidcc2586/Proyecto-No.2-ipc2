package apis;

import clasesAuxiliares.AsignacionEstudiantesServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.objetosRequest.AsignacionEstudianteRequest;

import java.sql.SQLException;

@Path("asignacionEstudiantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AsignacionEstudiantesResource {

    @POST
    @Path("asignarEstudiante")
    public Response asignarEstudiante(AsignacionEstudianteRequest asignacion){
        try {
            AsignacionEstudiantesServicio asignacionEstudiantesServicio = new AsignacionEstudiantesServicio();
            asignacionEstudiantesServicio.asignarEstudiante(asignacion);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }



}
