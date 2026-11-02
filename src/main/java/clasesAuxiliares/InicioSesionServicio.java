package clasesAuxiliares;

import objetos.enums.Estado;
import objetos.enums.EstadoEstudiante;
import objetos.enums.RolUsuario;
import objetos.exceptions.CredencialesInvalidasException;
import objetos.exceptions.UsuarioInactivoException;
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InicioSesionServicio {

    public InicioSesion validarCredenciales(InicioSesionRequest inicioSesionRequest) throws SQLException, CredencialesInvalidasException, UsuarioNoEncontradoException, UsuarioInactivoException {
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
            if (salario < 0) {
                throw new ValidacionException("El salario no puede ser negativo.");
            }
        } catch (NumberFormatException e) {
            throw new ValidacionException("El salario ingresado no es un número válido.");
        }

        LocalDate fechaNacimiento;
        LocalDate fechaContratacion;

        try {
            fechaNacimiento = parsearFecha(usuarioRequest.getFechaNacimiento());
            fechaContratacion = parsearFecha(usuarioRequest.getFechaContratacion());
        } catch (Exception e) {
            throw new ValidacionException("El formato de la fecha es inválido. Use el formato yyyy-MM-dd o dd/MM/yyyy.");
        }

        RolUsuario rol;
        try {
            rol = RolUsuario.valueOf(usuarioRequest.getRolUsuario().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidacionException("El rol seleccionado es inválido.");
        }


        Usuario nuevoUsuario = new Usuario(0, usuarioRequest.getCui(), usuarioRequest.getNombre(), usuarioRequest.getApellido(), fechaNacimiento, usuarioRequest.getUsuario(), usuarioRequest.getClave(), Estado.ACTIVO, rol, fechaContratacion, salario);
        UsuarioDB usuarioDB = new UsuarioDB();
        usuarioDB.ingresarNuevoUsuario(nuevoUsuario);
    }

    public void crearCuentaEstudiante(EstudianteRequest estudianteRequest) throws ValidacionException, SQLException {
        Estudiante nuevoEstudiante = crearEstudiante(estudianteRequest);
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

    private LocalDate parsearFecha(String fechaStr) {
        if (fechaStr == null || fechaStr.isEmpty()) {
            throw new IllegalArgumentException("La fecha es nula o vacía");
        }
        try {
            return LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            return LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
    }

    private Estudiante crearEstudiante(EstudianteRequest estudianteRequest) throws ValidacionException {
        if (estudianteRequest == null) {
            throw new ValidacionException("Completa los datos del estudiante.");
        }
        if (estudianteRequest.getClave() == null || estudianteRequest.getClaveConfirmacion() == null
            || !estudianteRequest.getClave().equals(estudianteRequest.getClaveConfirmacion())) {
            throw new ValidacionException("La clave y confirmación de clave no coinciden.");
        }
        if (estudianteRequest.getCui() == null || estudianteRequest.getCui().length() != 13) {
            throw new ValidacionException("Número de CUI inválido");
        }

        int anioInscripcion;
        LocalDate fechaNacimiento;
        try {
            anioInscripcion = Integer.parseInt(estudianteRequest.getAnioInscripcion());
            fechaNacimiento = LocalDate.parse(estudianteRequest.getFechaNacimiento());
        } catch (RuntimeException e) {
            throw new ValidacionException("El año de inscripción o la fecha de nacimiento son inválidos.");
        }
        if (estudianteRequest.getNombre() == null || estudianteRequest.getNombre().isBlank()
            || estudianteRequest.getApellido() == null || estudianteRequest.getApellido().isBlank()
            || estudianteRequest.getUsuario() == null || estudianteRequest.getUsuario().isBlank()
            || estudianteRequest.getTipoSangre() == null || estudianteRequest.getTipoSangre().isBlank()) {
            throw new ValidacionException("Completa los datos obligatorios del estudiante.");
        }

        return new Estudiante(0, estudianteRequest.getCui(), estudianteRequest.getNombre(), estudianteRequest.getApellido(), fechaNacimiento, estudianteRequest.getUsuario(), estudianteRequest.getClave(), anioInscripcion, EstadoEstudiante.ACTIVO, estudianteRequest.getTipoSangre(), estudianteRequest.getAlergia(), estudianteRequest.getNombreEncargado(), estudianteRequest.getTelefonoEncargado(), estudianteRequest.getCorreoEncargado());
    }
}
