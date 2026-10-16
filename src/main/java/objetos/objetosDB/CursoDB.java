package objetos.objetosDB;

import objetos.objetosResponse.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CursoDB {

    public List<Curso> todosLosCursos() throws SQLException {
        List<Curso> cursoResponses = new ArrayList<>();
        String sqlSolicitarCursos = "SELECT * FROM Curso";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarCursos);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                cursoResponses.add(crearCurso(resultSet));
            }
        }

        return cursoResponses;
    }

    public Optional<Curso> obtenerCurso(int idCurso) throws SQLException{
        Optional<Curso> curso = Optional.empty();
        String sqlSolicitarCursos = "SELECT * FROM Curso WHERE idCurso = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarCursos)){
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                curso = Optional.of(crearCurso(resultSet));
            }
            resultSet.close();
        }
        return curso;
    }

    public void ingresarNuevoCurso(String nombreCurso) throws SQLException{
        String sqlIngresarCurso = "insert into Curso(nombreCurso) VALUES(?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarCurso)){
            preparedStatement.setString(1,nombreCurso);
            preparedStatement.executeUpdate();
        }
    }

    private Curso crearCurso(ResultSet resultSet) throws SQLException{
        int idCurso = resultSet.getInt("idCurso");
        String nombreCurso = resultSet.getString("nombreCurso");
        return new Curso(idCurso,nombreCurso);
    }

}
