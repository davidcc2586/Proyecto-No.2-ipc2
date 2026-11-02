package objetos.objetosDB;

import objetos.UtilidadesBase64;
import objetos.enums.Estado;
import objetos.enums.RolUsuario;
import objetos.exceptions.CredencialesInvalidasException;
import objetos.exceptions.UsuarioInactivoException;
import objetos.objetosRequest.InicioSesionRequest;
import objetos.objetosRequest.RecuperarClaveRequest;
import objetos.objetosResponse.InicioSesion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InicioSesionDB {
    private final String USUARIO = "Usuario";
    private final String ESTUDIANTE = "Estudiante";


    public InicioSesion obtenerPerfil(InicioSesionRequest inicioSesionRequest) throws SQLException, CredencialesInvalidasException, UsuarioInactivoException {
        InicioSesion inicioSesion = obtenerPerfilUsuario(USUARIO,inicioSesionRequest);
        if (inicioSesion != null){
            return inicioSesion;
        }
        inicioSesion = obtenerPerfilUsuario(ESTUDIANTE,inicioSesionRequest);
        return inicioSesion;
    }

    private InicioSesion obtenerPerfilUsuario(String tipoUsuario, InicioSesionRequest inicioSesionRequest) throws SQLException, CredencialesInvalidasException, UsuarioInactivoException{

        try (Connection connection = ConnectionDB.getConexion()){
            String sqlSolicitarPerfil = "SELECT * From " + tipoUsuario + " WHERE usuario = ? AND clave = ?";
            UtilidadesBase64 utilidadesBase64 = new UtilidadesBase64();
            String claveCodificada = utilidadesBase64.codificar(inicioSesionRequest.getClave());
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarPerfil)) {
                preparedStatement.setString(1, inicioSesionRequest.getUsuario());
                preparedStatement.setString(2, claveCodificada);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    if (resultSet.getString("estado").equalsIgnoreCase(Estado.INACTIVO.name())){
                        throw new UsuarioInactivoException();
                    }
                    String sqlAnioLectivo = "SELECT anioLectivo FROM ConfiguracionColegio WHERE estado = ?";
                    int anio;
                    try (PreparedStatement preparedStatement1 = connection.prepareStatement(sqlAnioLectivo)) {
                        preparedStatement1.setString(1, Estado.ACTIVO.name());
                        ResultSet resultSet1 = preparedStatement1.executeQuery();
                        resultSet1.next();
                        anio = resultSet1.getInt("anioLectivo");
                    }
                    if (tipoUsuario.equalsIgnoreCase(USUARIO)) {
                        return new InicioSesion(resultSet.getInt("idUsuario"), resultSet.getString("usuario"), RolUsuario.valueOf(resultSet.getString("rol")), anio);
                    } else {
                        return new InicioSesion(resultSet.getInt("idEstudiante"), resultSet.getString("usuario"), RolUsuario.ESTUDIANTE, anio);
                    }
                }
                resultSet.close();
            }
            if (existePerfilUsuario(connection, tipoUsuario, "USUARIO", inicioSesionRequest.getUsuario())) {
                throw new CredencialesInvalidasException();
            }
            if (existePerfilUsuario(connection, tipoUsuario, "CLAVE", inicioSesionRequest.getUsuario())) {
                throw new CredencialesInvalidasException();
            }
        }
        return null;
    }
    private boolean existePerfilUsuario(Connection connection, String tipoUsuario, String filtro, String dato) throws SQLException{
        String sqlSolicitarEstudiantes = "SELECT * From " + tipoUsuario + " WHERE usuario = ?";
        if (filtro.equalsIgnoreCase("CLAVE")){
            sqlSolicitarEstudiantes = "SELECT * From " + tipoUsuario + " WHERE clave = ?";
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarEstudiantes)){
            preparedStatement.setString(1,dato);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
            resultSet.close();
        }
        return false;
    }

    public String recuperarClave(RecuperarClaveRequest recuperarClaveRequest) throws SQLException{
        String clave = obtenerClave(USUARIO,recuperarClaveRequest);
        UtilidadesBase64 utilidadesBase64 = new UtilidadesBase64();
        if (clave != null){
            return utilidadesBase64.decodificar(clave);
        }
        clave = obtenerClave(ESTUDIANTE,recuperarClaveRequest);
        if (clave != null){
            return utilidadesBase64.decodificar(clave);
        }
        return null;
    }

    private String obtenerClave(String tipoUsuario, RecuperarClaveRequest recuperarClaveRequest) throws SQLException{
        String sql = "SELECT clave FROM " + tipoUsuario + " WHERE usuario = ? AND fechaNacimiento = ? AND cui = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,recuperarClaveRequest.getUsuario());
            preparedStatement.setDate(2,java.sql.Date.valueOf(recuperarClaveRequest.getFechaNacimiento()));
            preparedStatement.setString(3,recuperarClaveRequest.getCui());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getString("clave");
            }
        }
        return null;
    }
}
