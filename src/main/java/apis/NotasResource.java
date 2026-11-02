package apis;

import clasesAuxiliares.NotasServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.exceptions.ValidacionException;
import objetos.objetosRequest.ActividadRequest;
import objetos.objetosRequest.NotaRequest;
import objetos.objetosResponse.Actividad;
import objetos.objetosResponse.NotaActividad;
import objetos.objetosResponse.PaginaResponse;

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
        @Path("actividadesPaginadas/{idCurso}/{idSeccion}")
        public Response actividadesPaginadas(@PathParam("idCurso") int idCurso, @PathParam("idSeccion") int idSeccion, @QueryParam("pagina") @DefaultValue("0") int pagina, @QueryParam("tamano") @DefaultValue("10") int tamano) {
            try {
                NotasServicio notasServicio = new NotasServicio();
                PaginaResponse<Actividad> actividades = notasServicio.actividadesPaginadas(idCurso, idSeccion, pagina, tamano);
                return Response.ok(actividades).build();
            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
            } catch (SQLException e) {
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

        @GET
        @Path("notasCursoPaginadas/{idActividad}")
        public Response notasPaginadas(@PathParam("idActividad") int idActividad, @QueryParam("pagina") @DefaultValue("0") int pagina, @QueryParam("tamano") @DefaultValue("10") int tamano) {
            try {
                NotasServicio notasServicio = new NotasServicio();
                PaginaResponse<NotaActividad> notas = notasServicio.notasActividadPaginadas(idActividad, pagina, tamano);
                return Response.ok(notas).build();
            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
            } catch (SQLException e) {
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

    @GET
    @Path("historialEstudiante/{idEstudiante}")
    public Response historialEstudiante(@PathParam("idEstudiante") int idEstudiante) {
        try {
            NotasServicio notasServicio = new NotasServicio();
            return Response.ok(notasServicio.historialEstudiante(idEstudiante)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"No se pudo consultar el historial de notas\"}").build();
        }
    }
}
