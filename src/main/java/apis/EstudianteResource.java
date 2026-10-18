package apis;

import clasesAuxiliares.EstudianteServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    @Path("estudiantesSeccion/{idCurso}")
    public Response estudiantesSeccion(@PathParam("idCurso") int idSeccion){
        try {
            EstudianteServicio estudianteServicio = new EstudianteServicio();
            return Response.ok(estudianteServicio.estudiantesSeccion(idSeccion)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        }
    }


}
