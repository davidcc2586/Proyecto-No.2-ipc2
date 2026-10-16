package clasesAuxiliares;

import objetos.enums.EstadoEstudiante;
import objetos.enums.RolUsuario;
import objetos.exceptions.CredencialesInvalidasException;
import objetos.exceptions.UsuarioNoEncontradoException;
import objetos.exceptions.ValidacionException;
import objetos.objetosDB.EstudianteDB;
import objetos.objetosRequest.EstudianteRequest;
import objetos.objetosRequest.RecuperarClaveRequest;
import objetos.objetosRequest.UsuarioRequest;
import objetos.objetosRequest.InicioSesionRequest;
import objetos.objetosResponse.Estudiante;
import objetos.objetosResponse.InicioSesion;
import objetos.objetosDB.InicioSesionDB;
import objetos.objetosDB.UsuarioDB;
import objetos.objetosResponse.RecuperarClaveResponse;
import objetos.objetosResponse.Usuario;

import java.sql.SQLException;
import java.time.LocalDate;

public class InicioSesionServicio {

    public InicioSesion validarCredenciales(InicioSesionRequest inicioSesionRequest) throws SQLException, CredencialesInvalidasException, UsuarioNoEncontradoException {
        InicioSesionDB inicioSesionDB = new InicioSesionDB();
        InicioSesion inicioSesion = inicioSesionDB.obtenerPerfil(inicioSesionRequest);
        if (inicioSesion == null) {
            throw new UsuarioNoEncontradoException();
        }
        return inicioSesion;
    }

    public void crearCuentaUsuario(UsuarioRequest usuarioRequest) throws ValidacionException, SQLException {

        if (!usuarioRequest.getClave().equalsIgnoreCase(usuarioRequest.getClaveConfirmacion())){
            throw new ValidacionException("La clave y confirmación de clave no coinciden.");
        }

        if (usuarioRequest.getCui().length() != 13) {
            throw new ValidacionException("Número de CUI inválido");
        }

        double salario;
        try {
            salario = Double.parseDouble(usuarioRequest.getSalario());
        } catch (NumberFormatException e) {
            throw new ValidacionException("El salario ingresado no es un número válido.");
        }

        if (salario < 0) {
            throw new ValidacionException("El salario no puede ser negativo.");
        }

        Usuario nuevoUsuario = new Usuario(0, usuarioRequest.getCui(), usuarioRequest.getNombre(), usuarioRequest.getApellido(), LocalDate.parse(usuarioRequest.getFechaNacimiento()), usuarioRequest.getUsuario(), usuarioRequest.getClave(), RolUsuario.valueOf(usuarioRequest.getRolUsuario()), LocalDate.parse(usuarioRequest.getFechaContratacion()), salario);

        UsuarioDB usuarioDB = new UsuarioDB();
        usuarioDB.ingresarNuevoUsuario(nuevoUsuario);
    }

    public void crearCuentaEstudiante(EstudianteRequest estudianteRequest) throws ValidacionException, SQLException {
        if (!estudianteRequest.getClave().equalsIgnoreCase(estudianteRequest.getClaveConfirmacion())){
            throw new ValidacionException("La clave y confirmación de clave no coinciden.");
        }
        if (estudianteRequest.getCui().length() != 13) {
            throw new ValidacionException("Número de CUI inválido");
        }
        try {
            Integer.parseInt(estudianteRequest.getAnioInscripcion());
        } catch (Exception e) {
            throw new ValidacionException("Año de inscripción invalido");
        }

        Estudiante nuevoEstudiante = new Estudiante(0,estudianteRequest.getCui(),estudianteRequest.getNombre(),estudianteRequest.getApellido(),LocalDate.parse(estudianteRequest.getFechaNacimiento()),estudianteRequest.getUsuario(),estudianteRequest.getClave(),Integer.parseInt(estudianteRequest.getAnioInscripcion()), EstadoEstudiante.ACTIVO,estudianteRequest.getTipoSangre(),estudianteRequest.getAlergia(),estudianteRequest.getNombreEncargado(),estudianteRequest.getTelefonoEncargado(),estudianteRequest.getCorreoEncargado());
        EstudianteDB estudianteDB = new EstudianteDB();
        estudianteDB.ingresarNuevoEstudiante(nuevoEstudiante);
    }

    public RecuperarClaveResponse obtenerClave(RecuperarClaveRequest recuperarClaveRequest) throws SQLException,ValidacionException{
        InicioSesionDB inicioSesionDB = new InicioSesionDB();
        String clave = inicioSesionDB.recuperarClave(recuperarClaveRequest);
        if (clave == null){
            throw new ValidacionException("Datos de usuario no encontrados");
        }
        return new RecuperarClaveResponse(clave);
    }
}
