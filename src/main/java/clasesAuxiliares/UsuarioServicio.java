package clasesAuxiliares;

import objetos.enums.RolUsuario;
import objetos.exceptions.ValidacionException;
import objetos.objetosDB.UsuarioDB;
import objetos.objetosRequest.UsuarioActualizarRequest;
import objetos.objetosResponse.Usuario;
import objetos.objetosResponse.UsuarioSelectResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioServicio {
    public List<Usuario> obtenerUsuarioSegunRol(RolUsuario rolUsuario) throws SQLException {
        UsuarioDB usuarioDB = new UsuarioDB();
        return usuarioDB.usuariosSegunRol(rolUsuario);
    }
    public List<UsuarioSelectResponse> resumenUsuarios(RolUsuario rolUsuario) throws SQLException {
        UsuarioDB usuarioDB = new UsuarioDB();
        List<Usuario> usuarios = usuarioDB.usuariosSegunRol(rolUsuario);
        List<UsuarioSelectResponse> usuariosSelect = new ArrayList<>();
        for (Usuario usuario : usuarios){
            usuariosSelect.add(new UsuarioSelectResponse(usuario.getIdUsuario(), usuario.getNombre() + " " + usuario.getApellido()));
        }
        return usuariosSelect;
    }
    public void actualizarUsuario(UsuarioActualizarRequest usuario) throws SQLException, ValidacionException {

        if (usuario.getClave() != null || usuario.getClaveConfirmacion() != null) {
            String clave = usuario.getClave();
            String claveConfirmacion = usuario.getClaveConfirmacion();

            if (clave == null || claveConfirmacion == null || !clave.equals(claveConfirmacion)) {
                throw new ValidacionException("Las contraseñas no coinciden o están incompletas");
            }
        }

        if (usuario.getSalario() != null && !usuario.getSalario().isEmpty()) {
            try {
                double salarioDouble = Double.parseDouble(usuario.getSalario());
                if (salarioDouble < 0) {
                    throw new ValidacionException("El salario no puede ser negativo");
                }
            } catch (NumberFormatException e) {
                throw new ValidacionException("El formato del salario no es válido");
            }
        }

        if (usuario.getRolUsuario() != null && !usuario.getRolUsuario().isEmpty()) {
            try {
                RolUsuario.valueOf(usuario.getRolUsuario());
            } catch (IllegalArgumentException e) {
                throw new ValidacionException("El rol especificado no es válido");
            }
        }

        UsuarioDB usuarioDB = new UsuarioDB();
        usuarioDB.actualizarDatosUsuario(usuario);
    }

    public void desactivarActivarUsuario(int idUsuario) throws SQLException{
        UsuarioDB usuarioDB = new UsuarioDB();
        usuarioDB.desactivarActivarUsuario(idUsuario);
    }

    public Usuario obtenerUsuario(int idUsuario) throws SQLException{
        UsuarioDB usuarioDB = new UsuarioDB();
        return usuarioDB.obtenerUsuario(idUsuario).get();
    }
}
