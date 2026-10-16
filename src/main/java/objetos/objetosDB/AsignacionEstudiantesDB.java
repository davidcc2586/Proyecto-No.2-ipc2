package objetos.objetosDB;

import objetos.objetosResponse.Estudiante;
import objetos.objetosResponse.AsignacionEstudiantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AsignacionEstudiantesDB {

    public List<Estudiante> obtenerEstudiantesSeccion(int idSeccion) throws SQLException{
        List<AsignacionEstudiantes> asignaciones = asignacionEstudiantesSeccion(idSeccion);
        EstudianteDB estudianteDB = new EstudianteDB();
        List<Estudiante> estudiantes = new ArrayList<>();
        for (AsignacionEstudiantes asignacionEstudiantes :asignaciones){
            Optional<Estudiante> estudiante = estudianteDB.obtenerEstudiante(asignacionEstudiantes.getIsEstudiante());
            if (estudiante.isPresent()){
                estudiantes.add(estudiante.get());
            }
        }
        return estudiantes;
    }

    public void desasignarEstudiante(int idEstudiante) throws SQLException{
        String sqlAsignarEstudiante = "DELETE FROM AsignacionEstudiantes WHERE idEstudiante = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlAsignarEstudiante)){
            preparedStatement.setInt(1,idEstudiante);
            preparedStatement.executeUpdate();
        }
    }
    public void asignarEstudiante(AsignacionEstudiantes asignacionEstudiantes) throws SQLException {
        String sqlIngresarAsignacion = "INSERT INTO AsignacionEstudiantes(idseccion, idestudiante) VALUES(?,?)";
        try(Connection connection = ConnectionDB.getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarAsignacion)) {
            preparedStatement.setInt(1, asignacionEstudiantes.getIdSeccion());
            preparedStatement.setInt(2, asignacionEstudiantes.getIsEstudiante());
            preparedStatement.executeUpdate();
        }
    }
    private List<AsignacionEstudiantes> asignacionEstudiantesSeccion(int idSeccion) throws SQLException{
        List<AsignacionEstudiantes> asignaciones = new ArrayList<>();
        String sqlSolicitarAsignarEstudiante = "SELECT * FROM AsignacionEstudiantes WHERE idSeccion = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarAsignarEstudiante)){
            preparedStatement.setInt(1,idSeccion);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                asignaciones.add(crearAsignacionEstudiate(resultSet));
            }
            resultSet.close();
        }
        return asignaciones;
    }

    private AsignacionEstudiantes crearAsignacionEstudiate(ResultSet resultSet)throws SQLException{
        int idSeccion = resultSet.getInt("idSeccion");
        int idEstudiante = resultSet.getInt("idEstudiante");
        return new AsignacionEstudiantes(idSeccion,idEstudiante);
    }
}
