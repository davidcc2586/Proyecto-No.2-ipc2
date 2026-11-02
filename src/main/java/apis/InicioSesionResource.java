package apis;

import clasesAuxiliares.InicioSesionServicio;
import clasesAuxiliares.SessionTokenService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import objetos.exceptions.CredencialesInvalidasException;
import objetos.exceptions.UsuarioInactivoException;
import objetos.exceptions.UsuarioNoEncontradoException;
import objetos.exceptions.ValidacionException;
import objetos.objetosRequest.EstudianteRequest;
import objetos.objetosRequest.RecuperarClaveRequest;
import objetos.objetosRequest.UsuarioRequest;
import objetos.objetosRequest.InicioSesionRequest;
import objetos.objetosResponse.InicioSesion;
import objetos.objetosResponse.SesionAutenticadaResponse;
import objetos.objetosResponse.RecuperarClaveResponse;

import java.sql.SQLException;

@Path("inicioSesion")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InicioSesionResource {

    @POST
    @Path("acceso")
    public Response iniciarSesion(InicioSesionRequest inicioSesionRequest){
        InicioSesionServicio inicioSesionServicio = new InicioSesionServicio();
        try {
            InicioSesion inicioSesion = inicioSesionServicio.validarCredenciales(inicioSesionRequest);
            SessionTokenService.IssuedToken token = SessionTokenService.issue(
                inicioSesion.getIdUsuario(),
                inicioSesion.getRolUsuario()
            );
            return Response.ok(new SesionAutenticadaResponse(inicioSesion, token.value(), token.expiresAt())).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        } catch (CredencialesInvalidasException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\": \"Usuario o contraseña incorrectos\"}").build();
        } catch (UsuarioNoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"Usuario no encontrado, cree uno nuevo o verifique sus credenciales\"}").build();
        } catch (UsuarioInactivoException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\": \"Usuario inactivo, consulte con administración el motivo\"}").build();
        }
    }

    @POST
    @Path("crearCuentaEstudiante")
    public Response crearCuentaEstudiante(EstudianteRequest estudianteRequest){
        InicioSesionServicio inicioSesionServicio = new InicioSesionServicio();
        try {
            inicioSesionServicio.crearCuentaEstudiante(estudianteRequest);
            return Response.status(Response.Status.CREATED).entity("{\"mensaje\": \"Cuenta creada con éxito\"}").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        } catch (ValidacionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("recuperarContraseña")
    public Response recuperarClave(RecuperarClaveRequest recuperarClaveRequest){
        InicioSesionServicio inicioSesionServicio = new InicioSesionServicio();
        try {
            RecuperarClaveResponse clave = inicioSesionServicio.obtenerClave(recuperarClaveRequest);
            return Response.ok(clave).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Error interno en el servidor\"}").build();
        } catch (ValidacionException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

}
