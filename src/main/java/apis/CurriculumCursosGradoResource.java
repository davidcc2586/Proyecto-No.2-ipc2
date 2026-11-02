package apis;

import clasesAuxiliares.CurriculumCursosGradoServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.exceptions.ValidacionException;
import objetos.objetosResponse.Curso;
import objetos.objetosResponse.PaginaResponse;
import objetos.objetosRequest.CurriculumCursosGradoRequest;

import java.sql.SQLException;

@Path("curriculumCursosGrado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CurriculumCursosGradoResource {

    @GET
    @Path("catalogo/{idGrado}")
    public Response cursosGrado(@PathParam("idGrado") int idGrado){
        try {
            CurriculumCursosGradoServicio curriculumCursosGradoServicio = new CurriculumCursosGradoServicio();
            return Response.ok(curriculumCursosGradoServicio.catalogo(idGrado)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

        @GET
        @Path("catalogoPaginado/{idGrado}")
        public Response cursosGradoPaginados(@PathParam("idGrado") int idGrado, @QueryParam("pagina") @DefaultValue("0") int pagina, @QueryParam("tamano") @DefaultValue("10") int tamano) {
            try {
                CurriculumCursosGradoServicio servicio = new CurriculumCursosGradoServicio();
                PaginaResponse<Curso> cursos = servicio.catalogoPaginado(idGrado, pagina, tamano);
                return Response.ok(cursos).build();
            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
            }
    }

    @POST
    @Path("asignarCursoGrado")
    public Response asignarCursoGrado(CurriculumCursosGradoRequest curriculumCursosGradoRequest){
        try {
            CurriculumCursosGradoServicio curriculumCursosGradoServicio = new CurriculumCursosGradoServicio();
            curriculumCursosGradoServicio.asignarCursoGrado(curriculumCursosGradoRequest);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }catch (ValidacionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
}
