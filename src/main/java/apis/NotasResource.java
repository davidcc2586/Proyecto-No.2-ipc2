package apis;

import clasesAuxiliares.NotasServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.exceptions.ValidacionException;
import objetos.objetosRequest.ActividadRequest;
import objetos.objetosRequest.NotaRequest;

import java.sql.SQLException;

@Path("notas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotasResource {

    @POST
    @Path("registrarActividad")
    public Response ingresarActividadZona(ActividadRequest actividadRequest){
        try {
            NotasServicio notasServicio = new NotasServicio();
            notasServicio.ingresarActividadZona(actividadRequest);
            return Response.ok().build();
        }catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        } catch (ValidacionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
    @GET
    @Path("actividades/{idCurso}/{idSeccion}")
    public Response actividades( @PathParam("idCurso") int idCurso,@PathParam("idSeccion") int idSeccion){
        try {
            NotasServicio notasServicio = new NotasServicio();
            return Response.ok(notasServicio.actividades(idCurso, idSeccion)).build();
        }catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @GET
    @Path("notasCurso/{idActividad}")
    public Response notas( @PathParam("idActividad") int idActividad){
        try {
            NotasServicio notasServicio = new NotasServicio();
            return Response.ok(notasServicio.notasActividad(idActividad)).build();
        }catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @POST
    @Path("modificarNota")
    public Response modificarNota(NotaRequest notaRequest){
        try {
            NotasServicio notasServicio = new NotasServicio();
            notasServicio.modificarNota(notaRequest);
            return Response.ok().build();
        }catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        } catch (ValidacionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
}
