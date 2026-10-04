package objetos.objetosDB;

import objetos.objetosClases.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDB {

    public List<Curso> todosLosCursos() throws SQLException {
        Connection connection = DBConnectionSingleton.getInstance().getConnection();
        List<Curso> cursos = new ArrayList<>();
        String sqlSolicitarCursos = "SELECT * FROM Curso";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarCursos);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                cursos.add(crearCurso(resultSet));
            }
        }

        return cursos;
    }

    private Curso crearCurso(ResultSet resultSet) throws SQLException{
        int idCurso = resultSet.getInt("idCurso");
        int idGrado = resultSet.getInt("IdGrado");
        String nombreCurso = resultSet.getString("nombreCurso");
        return new Curso(idCurso,idGrado,nombreCurso);
    }

}
