package objetos.objetosDB;

import objetos.enums.EstadoEstudiante;
import objetos.objetosResponse.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstudianteDB {

    public List<Estudiante> estudiantesSeccion(int idSeccion) throws SQLException{
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT * FROM Estudiante e JOIN AsignacionEstudiantes ae ON e.idEstudiante = ae.idEstudiante WHERE ae.idSeccion = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,idSeccion);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                estudiantes.add(crearEstudiante(resultSet));
            }
        }
        return estudiantes;
    }
    public List<Estudiante> obtenerEstudiantesSegunEstado(EstadoEstudiante estadoEstudiante) throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT * FROM Estudiante WHERE estado = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,estadoEstudiante.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                estudiantes.add(crearEstudiante(resultSet));
            }
        }
        return estudiantes;
    }

    public Optional<Estudiante> obtenerEstudiante(int idEstudiante) throws SQLException{
        Optional<Estudiante> estudiante = Optional.empty();
        String sqlSolicitarEstudiantes = "SELECT * From Estudiante  WHERE idEstudiante = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarEstudiantes)){
            preparedStatement.setInt(1,idEstudiante);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                estudiante = Optional.of(crearEstudiante(resultSet));
            }
        }
        return estudiante;
    }

    public void ingresarNuevoEstudiante(Estudiante estudiante) throws SQLException{
        String sqlIngresarEstudiante = "insert into Estudiante(cui, nombre, apellido, fechaNacimiento, usuario, clave, anioInscripcion, tipoSangre, alergias, nombreEncargado, telefonoEncargado, correoEncargado) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarEstudiante)){
            preparedStatement.setString(1, estudiante.getCui());
            preparedStatement.setString(2, estudiante.getNombre());
            preparedStatement.setString(3, estudiante.getApellido());
            preparedStatement.setDate(4,java.sql.Date.valueOf(estudiante.getFechaNacimiento()));
            preparedStatement.setString(5, estudiante.getUsuario());
            preparedStatement.setString(6, estudiante.getClave());
            preparedStatement.setInt(7, estudiante.getAnioInscripcion());
            preparedStatement.setString(8, estudiante.getTipoSangre());
            preparedStatement.setString(9, estudiante.getAlergia());
            preparedStatement.setString(10, estudiante.getNombreEncargado());
            preparedStatement.setString(11, estudiante.getTelefonoEncargado());
            preparedStatement.setString(12, estudiante.getCorreoEncargado());
            preparedStatement.executeUpdate();
        }
    }

    public void cambiarEstadoEstudiante(int idEstudiante,EstadoEstudiante estadoEstudiante) throws SQLException{
        String sqlCambiarEstado = "UPDATE Estudiante SET estado = ? WHERE idEstudiante = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCambiarEstado)){
            preparedStatement.setString(1,estadoEstudiante.name());
            preparedStatement.setInt(2,idEstudiante);
            preparedStatement.executeUpdate();
        }
    }
    private Estudiante crearEstudiante(ResultSet resultSet) throws SQLException {
        int idEstudiante = resultSet.getInt("idEstudiante");
        String cui = resultSet.getString("cui");
        String nombre = resultSet.getString("nombre");
        String apellido = resultSet.getString("apellido");
        LocalDate fechaNacimiento = resultSet.getDate("fechaNacimiento").toLocalDate();
        String usuario = resultSet.getString("usuario");
        String clave = resultSet.getString("clave");
        int anioInscripcion = resultSet.getInt("anioInscripcion");
        EstadoEstudiante estadoEstudiante = EstadoEstudiante.valueOf(resultSet.getString("estado"));
        String tipoSangre = resultSet.getString("tipoSangre");
        String nombreEncargado = resultSet.getString("nombreEncargado");
        String telefonoEncargado = resultSet.getString("telefonoEncargado");
        String alergia = resultSet.getString("alergias");
        String correoEncargado = resultSet.getString("correoEncargado");
        return new Estudiante(idEstudiante,cui,nombre,apellido,fechaNacimiento,usuario,clave,anioInscripcion,estadoEstudiante,tipoSangre,alergia,nombreEncargado,telefonoEncargado,correoEncargado);
    }
}
