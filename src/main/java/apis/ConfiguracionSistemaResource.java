package apis;

import clasesAuxiliares.ConfiguracionColegioServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("configurarSistema")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConfiguracionSistemaResource {

    @PATCH
    @Path("cambiarAnioLectivo/{anio}")
    public Response cambiarAnioLectivo(@PathParam("anio") int anio){
        try {
            ConfiguracionColegioServicio configuracionColegioServicio = new ConfiguracionColegioServicio();
            configuracionColegioServicio.cambiarAnioLectivo(anio);
            return Response.ok().entity("{\"mensaje\": \"Año lectivo actualizado con éxito\"}").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @GET
    @Path("añosLectivos")
    public Response cambiarAnioLectivo(){
        try {
            ConfiguracionColegioServicio configuracionColegioServicio = new ConfiguracionColegioServicio();
            return Response.ok(configuracionColegioServicio.obtenerAñosLectivos()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }
}
