package apis;

import clasesAuxiliares.SeccionServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.objetosRequest.SeccionActualizarRequest;
import objetos.objetosRequest.SeccionRequest;

import java.sql.SQLException;

@Path("seccion")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SeccionResource {

    @GET
    @Path("catalogoSeccion/{idGrado}")
    public Response seccionesGrado( @PathParam("idGrado") int idGrado){
        try {
            SeccionServicio seccionServicio = new SeccionServicio();
            return Response.ok(seccionServicio.seccionesGrado(idGrado)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @POST
    @Path("nuevoSeccion")
    public Response nuevaSeccion(SeccionRequest seccion){
        try {
            SeccionServicio seccionServicio = new SeccionServicio();
            seccionServicio.insertarNuevaSeccion(seccion);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @PUT
    @Path("actualizar")
    public Response actualizarSeccion(SeccionActualizarRequest seccion){
        try {
            SeccionServicio seccionServicio = new SeccionServicio();
            seccionServicio.actualizarSeccion(seccion);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @GET
    @Path("datosSeccion/{idSeccion}")
    public Response datosSeccion( @PathParam("idSeccion") int idSeccion){
        try {
            SeccionServicio seccionServicio = new SeccionServicio();
            return Response.ok(seccionServicio.obtenerSeccion(idSeccion)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }
}
