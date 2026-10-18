package apis;

import clasesAuxiliares.CarreraServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.objetosResponse.CarreraActualizarRequest;

import java.sql.SQLException;

@Path("carrera")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarreraResource {

    @GET
    @Path("catalogo")
    public Response carreras(){
        try {
            CarreraServicio carreraServicio = new CarreraServicio();
            return Response.ok(carreraServicio.catalogo()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @GET
    @Path("catalogo/{idCarrera}")
    public Response datosCarrera(@PathParam("idCarrera") int idCarrera){
        try {
            CarreraServicio carreraServicio = new CarreraServicio();
            return Response.ok(carreraServicio.obtenerLibro(idCarrera)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @POST
    @Path("nuevoCarrera")
    public Response nuevaCarrera(String nombreCarrera){
        try {
            CarreraServicio carreraServicio = new CarreraServicio();
            carreraServicio.nuevaCarrera(nombreCarrera);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @PUT
    @Path("actualizar")
    public Response actualizarCarrera(CarreraActualizarRequest carrera){
        try {
            CarreraServicio carreraServicio = new CarreraServicio();
            carreraServicio.actualizarCarrera(carrera);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @PUT
    @Path("activarDesactivarCarrera/{idCarrera}")
    public Response activarDesactivarCarrera(@PathParam("idCarrera") int idCarrera){
        try {
            CarreraServicio carreraServicio = new CarreraServicio();
            carreraServicio.activarDesactivarCarrera(idCarrera);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }
}
