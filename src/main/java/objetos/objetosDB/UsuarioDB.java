package objetos.objetosDB;

import objetos.enums.Estado;
import objetos.enums.RolUsuario;
import objetos.exceptions.ValidacionException;
import objetos.objetosRequest.UsuarioActualizarRequest;
import objetos.objetosResponse.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDB {

    public List<Usuario> usuariosSegunRol(RolUsuario rolUsuario) throws SQLException {
        List<Usuario> usuarioResponses = new ArrayList<>();
        String sqlSolicitarUsuarios = "SELECT * FROM Usuario WHERE rol = ?";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarUsuarios)) {
            preparedStatement.setString(1, rolUsuario.name());
             ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                usuarioResponses.add(crearUsuario(resultSet));
            }
        }

        return usuarioResponses;
    }
    public void ingresarNuevoUsuario(Usuario usuario) throws SQLException{
        String sqlIngresarEstudiante = "insert into Usuario(cui, nombre, apellido, fechaNacimiento, usuario, clave, fechaContratacion, salario) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarEstudiante)){
            preparedStatement.setString(1, usuario.getCui());
            preparedStatement.setString(2, usuario.getNombre());
            preparedStatement.setString(3, usuario.getApellido());
            preparedStatement.setDate(4,java.sql.Date.valueOf(usuario.getFechaNacimiento()));
            preparedStatement.setString(5, usuario.getUsuario());
            preparedStatement.setString(6, usuario.getClave());
            preparedStatement.setDate(7,java.sql.Date.valueOf(usuario.getFechaContratacion()));
            preparedStatement.setDouble(8, usuario.getSalario());
            preparedStatement.executeUpdate();
        }
    }

    public void actualizarDatosUsuario(UsuarioActualizarRequest usuario) throws SQLException,ValidacionException {
        StringBuilder sql = new StringBuilder("UPDATE Usuario SET ");
        List<Object> parametros = new ArrayList<>();
        boolean hayCampos = false;

        if (usuario.getClave() != null) {
            sql.append("clave = ?");
            parametros.add(usuario.getClave());
            hayCampos = true;
        }
        if (usuario.getUsuario() != null && !usuario.getUsuario().isEmpty()) {
            if (hayCampos) sql.append(", ");
            sql.append("usuario = ?");
            parametros.add(usuario.getUsuario());
            hayCampos = true;
        }
        if (usuario.getSalario() != null) {
            if (hayCampos) sql.append(", ");
            sql.append("salario = ?");
            parametros.add(Double.parseDouble(usuario.getSalario()));
            hayCampos = true;
        }
        if (usuario.getRolUsuario() != null) {
            if (hayCampos) sql.append(", ");
            sql.append("rol = ?");
            parametros.add(usuario.getRolUsuario());
            hayCampos = true;
        }
        if (!hayCampos) {
            throw new ValidacionException("No se proporcionó ningun campo para actualizar.");
        }

        sql.append(" WHERE idUsuario = ?");
        parametros.add(Integer.parseInt(usuario.getIdUsuario()));

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                preparedStatement.setObject(i + 1, parametros.get(i));
            }
            preparedStatement.executeUpdate();
        }
    }

    public void desactivarActivarUsuario(int idUsuario) throws SQLException{
        Estado estado;
        String sqlSolicitarEstadoActual = "SELECT estado FROM Usuario WHERE idUsuario = ?";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarEstadoActual)){
            preparedStatement.setInt(1,idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            estado = Estado.valueOf(resultSet.getString("estado"));
        }

        String sqlCambiarEstado = "UPDATE Usuario SET estado = ? WHERE idUsuario = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCambiarEstado)){
            if (estado.equals(Estado.ACTIVO)){
                preparedStatement.setString(1,Estado.INACTIVO.name());
            }else{
                preparedStatement.setString(1,Estado.ACTIVO.name());
            }
            preparedStatement.setInt(2,idUsuario);
            preparedStatement.executeUpdate();
        }
    }

    private Usuario crearUsuario(ResultSet resultSet) throws SQLException{
        int idUsuario = resultSet.getInt("idUsuario");
        String cui = resultSet.getString("cui");
        String nombre = resultSet.getString("nombre");
        String apellido = resultSet.getString("apellido");
        LocalDate fechaNacimiento = resultSet.getDate("fechaNacimiento").toLocalDate();
        String usuario = resultSet.getString("usuario");
        String clave = resultSet.getString("clave");
        RolUsuario rolUsuario = RolUsuario.valueOf(resultSet.getString("rol"));
        LocalDate fechaContratacion = resultSet.getDate("fechaContratacion").toLocalDate();
        double salario = resultSet.getDouble("salario");
        return new Usuario(idUsuario,cui,nombre,apellido,fechaNacimiento,usuario,clave,rolUsuario,fechaContratacion,salario);
    }
}
