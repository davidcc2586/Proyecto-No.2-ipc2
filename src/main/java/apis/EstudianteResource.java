package apis;

import clasesAuxiliares.EstudianteServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.enums.EstadoEstudiante;
import objetos.exceptions.ValidacionException;
import objetos.objetosRequest.EstudianteActualizarRequest;
import objetos.objetosResponse.Estudiante;
import objetos.objetosResponse.PaginaResponse;

import java.sql.SQLException;

@Path("estudiante")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstudianteResource {

    @GET
    @Path("selects/{estadoEstudiante}")
    public Response estudianteResumen(@PathParam("estadoEstudiante") String estadoEstudiante){
        try {
            EstudianteServicio estudianteServicio = new EstudianteServicio();
            return Response.ok(estudianteServicio.resumenEstudiantes(estadoEstudiante)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        }
    }

        @GET
        @Path("estudiantesPaginados/{estadoEstudiante}")
        public Response estudiantesPaginados(@PathParam("estadoEstudiante") String estadoEstudiante, @QueryParam("pagina") @DefaultValue("0") int pagina, @QueryParam("tamano") @DefaultValue("10") int tamano) {
            try {
                EstudianteServicio estudianteServicio = new EstudianteServicio();
                PaginaResponse<Estudiante> estudiantes = estudianteServicio.estudiantesPorEstadoPaginados(estadoEstudiante, pagina, tamano);
                return Response.ok(estudiantes).build();
            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
            }
    }

    @GET
    @Path("estudiantesSeccion/{idSeccion}")
    public Response estudiantesSeccion(@PathParam("idSeccion") int idSeccion){
        try {
            EstudianteServicio estudianteServicio = new EstudianteServicio();
            return Response.ok(estudianteServicio.estudiantesSeccion(idSeccion)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        }
    }

        @GET
        @Path("estudiantesSeccionPaginados/{idSeccion}")
        public Response estudiantesSeccionPaginados(@PathParam("idSeccion") int idSeccion, @QueryParam("pagina") @DefaultValue("0") int pagina, @QueryParam("tamano") @DefaultValue("10") int tamano) {
            try {
                EstudianteServicio estudianteServicio = new EstudianteServicio();
                PaginaResponse<Estudiante> estudiantes = estudianteServicio.estudiantesSeccionPaginados(idSeccion, pagina, tamano);
                return Response.ok(estudiantes).build();
            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
            }
    }

    @GET
    @Path("datosEstudiante/{idEstudiante}")
    public Response datosEstudiante(@PathParam("idEstudiante") int idEstudiante){
        try {
            EstudianteServicio estudianteServicio = new EstudianteServicio();
            return Response.ok(estudianteServicio.datosEstudiante(idEstudiante)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        }
    }


    @PUT
    @Path("actualizarEstudiante")
    public Response actualizarEstudiante(EstudianteActualizarRequest estudiante){
        try {
            EstudianteServicio estudianteServicio = new EstudianteServicio();
            estudianteServicio.actualizarEstudiante(estudiante);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        } catch (ValidacionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @PATCH
    @Path("cambiaEstadoEstudiante/{idEstudiante}/{estadoEstudiante}")
    public Response cambiaEstadoEstudiante(@PathParam("idEstudiante")int idEstudiante, @PathParam("estadoEstudiante")EstadoEstudiante estadoEstudiante){
        try {
            EstudianteServicio estudianteServicio = new EstudianteServicio();
            estudianteServicio.cambiaEstadoEstudiante(idEstudiante, estadoEstudiante);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        }
    }

}
