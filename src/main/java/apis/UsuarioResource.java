package apis;

import clasesAuxiliares.UsuarioServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.enums.RolUsuario;
import objetos.exceptions.ValidacionException;
import objetos.objetosRequest.UsuarioActualizarRequest;
import objetos.objetosResponse.Usuario;
import objetos.objetosResponse.UsuarioSelectResponse;

import java.sql.SQLException;
import java.util.List;

@Path("usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @GET
    @Path("clasificar/{rolUsuario}")
    public Response usuariosClasificadoSegunRol(@PathParam("rolUsuario") String rolUsuario) {
        try {
            UsuarioServicio usuarioServicio = new UsuarioServicio();
            List<Usuario> usuarios = usuarioServicio.obtenerUsuarioSegunRol(RolUsuario.valueOf(rolUsuario));
            return Response.ok(usuarios).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }
    @GET
    @Path("selects/{rolUsuario}")
    public Response usuarioResumen(@PathParam("rolUsuario") String rolUsuario) {
        try {
            UsuarioServicio usuarioServicio = new UsuarioServicio();
            List<UsuarioSelectResponse> usuarios = usuarioServicio.resumenUsuarios(RolUsuario.valueOf(rolUsuario));
            return Response.ok(usuarios).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @PUT
    @Path("actualizarUsuario")
    public Response actualizarUsuario(UsuarioActualizarRequest usuario){
        try {
            UsuarioServicio usuarioServicio = new UsuarioServicio();
            usuarioServicio.actualizarUsuario(usuario);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        } catch (ValidacionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @PATCH
    @Path("desactivarActivar/{id}")
    public Response desactivarActivarUsuario(@PathParam("id") int idUsuario) {
        try {
            UsuarioServicio usuarioServicio = new UsuarioServicio();
            usuarioServicio.desactivarActivarUsuario(idUsuario);
            return Response.ok().entity("{\"mensaje\": \"Usuario desactivado/activado correctamente\"}").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        }
    }


}
