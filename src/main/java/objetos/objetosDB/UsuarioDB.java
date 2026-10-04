package objetos.objetosDB;

import objetos.enums.RolUsuario;
import objetos.objetosClases.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDB {

    public List<Usuario> todosLosUsuarios() throws SQLException {
        Connection connection = DBConnectionSingleton.getInstance().getConnection();
        List<Usuario> usuarios = new ArrayList<>();
        String sqlSolicitarUsuarios = "SELECT * FROM Usuario";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarUsuarios);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                usuarios.add(crearUsuario(resultSet));
            }
        }

        return usuarios;
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
