package apis;

import clasesAuxiliares.GradoServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.objetosRequest.GradoActualizarRequest;
import objetos.objetosRequest.GradoRequest;
import objetos.objetosResponse.Grado;
import objetos.objetosResponse.PaginaResponse;

import java.sql.SQLException;

@Path("grado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GradoResource {

    @GET
    @Path("catalogo")
    public Response grados(){
        try {
            GradoServicio gradoServicio = new GradoServicio();
            return Response.ok(gradoServicio.catalogo()).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

        @GET
        @Path("catalogoPaginado")
        public Response gradosPaginados(@QueryParam("pagina") @DefaultValue("0") int pagina, @QueryParam("tamano") @DefaultValue("10") int tamano) {
            try {
                GradoServicio gradoServicio = new GradoServicio();
                PaginaResponse<Grado> grados = gradoServicio.catalogoPaginado(pagina, tamano);
                return Response.ok(grados).build();
            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
            }
    }

    @GET
    @Path("selectGrados")
    public Response selectGrados(){
        try {
            GradoServicio gradoServicio = new GradoServicio();
            return Response.ok(gradoServicio.selectGrados()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @POST
    @Path("nuevoGrado")
    public Response nuevoGrado(GradoRequest grado){
        try {
            GradoServicio gradoServicio = new GradoServicio();
            gradoServicio.nuevoGrado(grado);
            return Response.ok().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @PUT
    @Path("actualizar")
    public Response actualizarGrado(GradoActualizarRequest grado){
        try {
            GradoServicio gradoServicio = new GradoServicio();
            gradoServicio.actualizarGrado(grado);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @GET
    @Path("datosGrado/{idGrado}")
    public Response datosGrado(@PathParam("idGrado") int idGrado){
        try {
            GradoServicio gradoServicio = new GradoServicio();
            return Response.ok(gradoServicio.obtenerGrado(idGrado)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

}
