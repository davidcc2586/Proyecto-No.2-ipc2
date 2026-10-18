package apis;

import clasesAuxiliares.CursoServicio;
import clasesAuxiliares.EstudianteServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("curso")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CursoResource {

    @GET
    @Path("cursosMaestro/{idMaestro}")
    public Response cursosMaestro(@PathParam("idMaestro") int idMaestro){
        try {
            CursoServicio cursoServicio = new CursoServicio();
            return Response.ok(cursoServicio.cursosMaestro(idMaestro)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        }
    }

    @GET
    @Path("cursosEstudiante/{idEstudiante}")
    public Response cursosEstudiante(@PathParam("idEstudiante") int idEstudiante){
        try {
            CursoServicio cursoServicio = new CursoServicio();
            return Response.ok(cursoServicio.cursosEstudiante(idEstudiante)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        }
    }

    @POST
    @Path("ingresarNuevocurso")
    public Response ingresarNuevoCurso(String nombreCurso){
        try {
            CursoServicio cursoServicio = new CursoServicio();
            cursoServicio.ingresarNuevoCurso(nombreCurso);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        }
    }

    @GET
    @Path("cursos/{estado}")
    public Response cursosDisponibles(@PathParam("estado") String estado){
        try {
            CursoServicio cursoServicio = new CursoServicio();
            return Response.ok(cursoServicio.cursosDisponibles(estado)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        }
    }

    @PATCH
    @Path("activarDesactivarCurso/{idCurso}")
    public Response activarDesactivarCurso(@PathParam("idCurso") int idCurso){
        try {
            CursoServicio cursoServicio = new CursoServicio();
            cursoServicio.activarDesactivarCurso(idCurso);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        }
    }

    @PATCH
    @Path("activarDesactivarCurso/{idCurso}/{nuevoNombre}")
    public Response actualizarCurso(@PathParam("idCurso") int idCurso, @PathParam("nuevoNombre") String nuevoNombre){
        try {
            CursoServicio cursoServicio = new CursoServicio();
            cursoServicio.actualizarCurso(idCurso, nuevoNombre);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        }
    }


    @GET
    @Path("datosCurso/{idCurso}")
    public Response datosCurso(@PathParam("idCurso") int idCurso){
        try {
            CursoServicio cursoServicio = new CursoServicio();
            return Response.ok(cursoServicio.obtenerCurso(idCurso)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        }
    }

}
