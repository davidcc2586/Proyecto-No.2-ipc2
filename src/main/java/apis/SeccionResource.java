package apis;

import clasesAuxiliares.SeccionServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.exceptions.ValidacionException;
import objetos.objetosResponse.PaginaResponse;
import objetos.objetosRequest.SeccionActualizarRequest;
import objetos.objetosRequest.SeccionRequest;
import objetos.objetosResponse.Seccion;

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

        @GET
        @Path("catalogoSeccionPaginado/{idGrado}")
        public Response seccionesGradoPaginadas(@PathParam("idGrado") int idGrado, @QueryParam("pagina") @DefaultValue("0") int pagina, @QueryParam("tamano") @DefaultValue("10") int tamano) {
            try {
                SeccionServicio seccionServicio = new SeccionServicio();
                PaginaResponse<Seccion> secciones = seccionServicio.seccionesGradoPaginadas(idGrado, pagina, tamano);
                return Response.ok(secciones).build();
            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
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
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        } catch (ValidacionException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
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
