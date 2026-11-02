package objetos.objetosDB;

import objetos.objetosRequest.CurriculumCursosGradoRequest;
import objetos.objetosResponse.CurriculumCursosGrado;
import objetos.objetosResponse.Curso;
import objetos.objetosResponse.PaginaResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurriculumCursosGradoDB {

    public List<Curso> cursosCurriculumGrado(int idGrado) throws SQLException{
        List<CurriculumCursosGrado> curriculumCursosGradoRespons = curriculumGrado(idGrado);
        CursoDB cursoDB = new CursoDB();
        List<Curso> cursoResponses = new ArrayList<>();
        for (CurriculumCursosGrado curriculumCursosGrado : curriculumCursosGradoRespons){
            Optional<Curso> curso = cursoDB.obtenerCurso(curriculumCursosGrado.getIdCurso());
            if (curso.isPresent()){
                cursoResponses.add(curso.get());
            }
        }
        return cursoResponses;
    }

    public PaginaResponse<Curso> cursosCurriculumGradoPaginados(int idGrado, int pagina, int tamano) throws SQLException {
        int desplazamiento = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<Curso> cursos = new ArrayList<>();
        int totalElementos = 0;
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement conteo = connection.prepareStatement("SELECT COUNT(*) FROM CurriculumCursosGrado WHERE idGrado = ?")) {
            conteo.setInt(1, idGrado);
            try (ResultSet resultado = conteo.executeQuery()) {
                if (resultado.next()) {
                    totalElementos = resultado.getInt(1);
                }
            }
            String sql = "SELECT curso.* FROM CurriculumCursosGrado curriculum JOIN Curso curso ON curriculum.idCurso = curso.idCurso WHERE curriculum.idGrado = ? ORDER BY curso.idCurso LIMIT ? OFFSET ?";
            try (PreparedStatement consulta = connection.prepareStatement(sql)) {
                consulta.setInt(1, idGrado);
                consulta.setInt(2, tamano);
                consulta.setInt(3, desplazamiento);
                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        cursos.add(new Curso(resultado.getInt("idCurso"), resultado.getString("nombreCurso")));
                    }
                }
            }
        }
        return new PaginaResponse<>(cursos, pagina, tamano, totalElementos);
    }

    private List<CurriculumCursosGrado> curriculumGrado(int idGrado) throws SQLException {
        List<CurriculumCursosGrado> CurriculumsCursosGrado = new ArrayList<>();
        String sqlSolicitarCurriculumCursosGrado = "SELECT * FROM CurriculumCursosGrado WHERE idGrado = ?";
        try(Connection connection = ConnectionDB.getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarCurriculumCursosGrado)){
            preparedStatement.setInt(1,idGrado);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                CurriculumsCursosGrado.add(crearCurriculumCursosGrado(resultSet));
            }
        }

        return CurriculumsCursosGrado;
    }
    public void ingresarNuevoCursoCurriculum(CurriculumCursosGradoRequest curriculumCursosGrado) throws SQLException{
        String sqlIngresar = "insert into CurriculumCursosGrado(idGrado, idCurso) VALUES(?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresar)){
            preparedStatement.setInt(1, Integer.parseInt(curriculumCursosGrado.getIdGrado()));
            preparedStatement.setInt(2, Integer.parseInt(curriculumCursosGrado.getIdCurso()));
            preparedStatement.executeUpdate();
        }
    }

    public Boolean existeAsignacion(CurriculumCursosGradoRequest curriculumCursosGradoRequest) throws SQLException{
        String sql = "SELECT COUNT(*) FROM CurriculumCursosGrado WHERE idGrado = ? ANd idCurso = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, Integer.parseInt(curriculumCursosGradoRequest.getIdGrado()));
            preparedStatement.setInt(2, Integer.parseInt(curriculumCursosGradoRequest.getIdCurso()));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int conteo = resultSet.getInt(1);
                    return conteo > 0;
                }
                return false;
            }
        }
    }
    private CurriculumCursosGrado crearCurriculumCursosGrado(ResultSet resultSet) throws SQLException{
        int idGrado = resultSet.getInt("idGrado");
        int idCurso = resultSet.getInt("idCurso");
        return new CurriculumCursosGrado(idGrado,idCurso);
    }

}
