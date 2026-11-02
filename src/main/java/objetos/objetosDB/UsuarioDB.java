package objetos.objetosDB;

import objetos.UtilidadesBase64;
import objetos.enums.Estado;
import objetos.enums.RolUsuario;
import objetos.exceptions.ValidacionException;
import objetos.objetosRequest.UsuarioActualizarRequest;
import objetos.objetosResponse.Usuario;
import objetos.objetosResponse.PaginaResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public PaginaResponse<Usuario> usuariosSegunRolPaginados(RolUsuario rolUsuario, int pagina, int tamano) throws SQLException {
        int offset = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<Usuario> usuarios = new ArrayList<>();
        int totalElementos = 0;
        String sqlConteo = "SELECT COUNT(*) FROM Usuario WHERE rol = ?";
        String sqlUsuarios = "SELECT * FROM Usuario WHERE rol = ? ORDER BY idUsuario LIMIT ? OFFSET ?";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement conteo = connection.prepareStatement(sqlConteo)) {
            conteo.setString(1, rolUsuario.name());
            try (ResultSet resultado = conteo.executeQuery()) {
                if (resultado.next()) {
                    totalElementos = resultado.getInt(1);
                }
            }

            try (PreparedStatement consulta = connection.prepareStatement(sqlUsuarios)) {
                consulta.setString(1, rolUsuario.name());
                consulta.setInt(2, tamano);
                consulta.setInt(3, offset);
                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        usuarios.add(crearUsuario(resultado));
                    }
                }
            }
        }
        return new PaginaResponse<>(usuarios, pagina, tamano, totalElementos);
    }
    public void ingresarNuevoUsuario(Usuario usuario) throws SQLException {
        String sqlIngresarEstudiante = "insert into Usuario(cui, nombre, apellido, fechaNacimiento, usuario, clave, fechaContratacion, salario, rol) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarEstudiante)){
            UtilidadesBase64 utilidadesBase64 = new UtilidadesBase64();
            preparedStatement.setString(1, usuario.getCui());
            preparedStatement.setString(2, usuario.getNombre());
            preparedStatement.setString(3, usuario.getApellido());
            preparedStatement.setDate(4, java.sql.Date.valueOf(usuario.getFechaNacimiento()));
            preparedStatement.setString(5, usuario.getUsuario());
            preparedStatement.setString(6, utilidadesBase64.codificar(usuario.getClave()));
            preparedStatement.setDate(7, java.sql.Date.valueOf(usuario.getFechaContratacion()));
            preparedStatement.setDouble(8, usuario.getSalario());
            preparedStatement.setString(9,usuario.getRolUsuario().name());
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

    public void desactivarActivarUsuario(int idUsuario) throws SQLException, ValidacionException{
        Estado estado;
        RolUsuario rolUsuario;
        String sqlSolicitarEstadoActual = "SELECT estado, rol FROM Usuario WHERE idUsuario = ?";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarEstadoActual)){
            preparedStatement.setInt(1,idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            estado = Estado.valueOf(resultSet.getString("estado"));
            rolUsuario = RolUsuario.valueOf(resultSet.getString("rol"));
        }
        if (rolUsuario.equals(RolUsuario.SUPERADMIN) && estado.equals(Estado.ACTIVO)){
            if(1 == cantidadSuperAdmin()){
                throw new ValidacionException("Siempre tiene que haber un Super Admin activo.");
            }
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

    private int cantidadSuperAdmin() throws SQLException{
        String sql = "SELECT count(*) FROM Usuario WHERE rol = ? AND estado = ?";
        try (Connection connection = ConnectionDB.getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,RolUsuario.SUPERADMIN.name());
            preparedStatement.setString(2,Estado.INACTIVO.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    public Optional<Usuario> obtenerUsuario(int idUsuario) throws SQLException{
        Optional<Usuario> usuario;
        String sql = "SELECT * FROM Usuario WHERE idUsuario = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            usuario = Optional.of(crearUsuario(resultSet));
        }
        return usuario;
    }

    private Usuario crearUsuario(ResultSet resultSet) throws SQLException{
        int idUsuario = resultSet.getInt("idUsuario");
        String cui = resultSet.getString("cui");
        String nombre = resultSet.getString("nombre");
        String apellido = resultSet.getString("apellido");
        LocalDate fechaNacimiento = resultSet.getDate("fechaNacimiento").toLocalDate();
        String usuario = resultSet.getString("usuario");
        String clave = resultSet.getString("clave");
        Estado estado = Estado.valueOf(resultSet.getString("estado"));
        RolUsuario rolUsuario = RolUsuario.valueOf(resultSet.getString("rol"));
        LocalDate fechaContratacion = resultSet.getDate("fechaContratacion").toLocalDate();
        double salario = resultSet.getDouble("salario");
        return new Usuario(idUsuario,cui,nombre,apellido,fechaNacimiento,usuario,clave,estado,rolUsuario,fechaContratacion,salario);
    }
}
