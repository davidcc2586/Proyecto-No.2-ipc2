package objetos.objetosDB;

import objetos.objetosRequest.NotaRequest;
import objetos.objetosResponse.Estudiante;
import objetos.objetosResponse.NotaActividad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotaActividadDB {

    public List<NotaActividad> notasActividad(int idActividad) throws SQLException{
        List<NotaActividad> notas = new ArrayList<>();
        String sql = "SELECT  * FROM NotaActividad WHERE idActividad = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,idActividad);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                notas.add(crearNotaActividad(resultSet));
            }
        }
        return notas;
    }

    public void modificarNota(NotaRequest nota) throws SQLException{
        String sql = "UPDATE NotaActividad set punteoObtenido = ? WHERE idEstudiante = ? AND idActividad = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setDouble(1, Double.parseDouble(nota.getPunteoObtenido()));
            preparedStatement.setInt(2, Integer.parseInt(nota.getIdEstudiante()));
            preparedStatement.setInt(3,Integer.parseInt(nota.getIdActividad()));
            preparedStatement.executeUpdate();
        }
    }


    public void actualizarNotasEstudiantes(Connection connection, int idActividad, int idSeccion) throws SQLException{
        List<Integer> idEstudiantesSeccion = idEstudiantesSeccion(idSeccion);
        String sqlCrearNotasEstudiantes = "INSERT INTO NotaActividad(idActividad, idEstudiante) VALUES (?,?)";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sqlCrearNotasEstudiantes);){
            preparedStatement.setInt(1, idActividad);
            for (int idEstudiante : idEstudiantesSeccion){
                preparedStatement.setInt(2,idEstudiante);
                preparedStatement.executeUpdate();
            }
        }
    }

    private List<Integer>  idEstudiantesSeccion(int idSeccion) throws SQLException{
        List<Integer> idEstudiantes = new ArrayList<>();
        EstudianteDB estudianteDB = new EstudianteDB();
        for (Estudiante estudiante : estudianteDB.estudiantesSeccion(idSeccion)){
            idEstudiantes.add(estudiante.getIdEstudiante());
        }
        return idEstudiantes;
    }
    private NotaActividad crearNotaActividad(ResultSet resultSet) throws SQLException {
        int idActividad = resultSet.getInt("idActividad");
        int idEstudiante = resultSet.getInt("idEstudiante");
        double punteoObtenido = resultSet.getDouble("punteoObtenido");
        return new NotaActividad(idActividad,idEstudiante,punteoObtenido);
    }
}
