package objetos.objetosDB;

import objetos.enums.EstadoEstudiante;
import objetos.objetosClases.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDB {

    public List<Estudiante> todosLosEstudiantes() throws SQLException{
        Connection connection = DBConnectionSingleton.getInstance().getConnection();
        List<Estudiante> estudiantes = new ArrayList<>();
        String sqlSolicitarEstudiantes = "SELECT * From Estudiante";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarEstudiantes);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                estudiantes.add(crearEstudiante(resultSet));
            }
        }

        return estudiantes;
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
