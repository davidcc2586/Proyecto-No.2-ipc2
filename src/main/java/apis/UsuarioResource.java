package apis;

import clasesAuxiliares.InicioSesionServicio;
import clasesAuxiliares.PagoServicio;
import clasesAuxiliares.UsuarioServicio;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.enums.RolUsuario;
import objetos.exceptions.ValidacionException;
import objetos.objetosRequest.UsuarioActualizarRequest;
import objetos.objetosRequest.UsuarioRequest;
import objetos.objetosResponse.Usuario;
import objetos.objetosResponse.UsuarioSelectResponse;
import objetos.objetosResponse.PaginaResponse;

import java.sql.SQLException;
import java.util.List;

@Path("usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {


    @GET
    @Path("selectSecretarios")
    public Response selectSecretarios() {
        try {
            PagoServicio pagoServicio = new PagoServicio();
            return Response.ok(pagoServicio.selectSecretarios()).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

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
        @Path("clasificarPaginado/{rolUsuario}")
        public Response usuariosClasificadoSegunRolPaginado(@PathParam("rolUsuario") String rolUsuario, @QueryParam("pagina") @DefaultValue("0") int pagina, @QueryParam("tamano") @DefaultValue("10") int tamano) {
            try {
                UsuarioServicio usuarioServicio = new UsuarioServicio();
                PaginaResponse<Usuario> usuarios = usuarioServicio.obtenerUsuarioSegunRolPaginado(RolUsuario.valueOf(rolUsuario), pagina, tamano);
                return Response.ok(usuarios).build();
            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
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
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error en el servidor\"}").build();
        } catch (ValidacionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("datosUsuario/{idUsuario}")
    public Response datosUsuario(@PathParam("idUsuario") int idUsuario) {
        try {
            UsuarioServicio usuarioServicio = new UsuarioServicio();
            return Response.ok(usuarioServicio.obtenerUsuario(idUsuario)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        }
    }

    @POST
    @Path("crearCuentaUsuario")
    public Response crearCuentaUsuario(UsuarioRequest usuarioRequest){
        InicioSesionServicio inicioSesionServicio = new InicioSesionServicio();
        try {
            inicioSesionServicio.crearCuentaUsuario(usuarioRequest);
            return Response.status(Response.Status.CREATED).entity("{\"mensaje\": \"Cuenta creada con éxito\"}").build();
        } catch (ValidacionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
