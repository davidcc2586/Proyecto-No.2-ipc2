package objetos.objetosDB;

import objetos.enums.Estado;
import objetos.objetosResponse.Curso;
import objetos.objetosResponse.PaginaResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CursoDB {


    public List<Curso> cursosDisponibles(String estado) throws SQLException{
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM Curso WHERE estado = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, estado);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cursos.add(crearCurso(resultSet));
            }
        }
        return cursos;
    }

    public PaginaResponse<Curso> cursosDisponiblesPaginados(String estado, int pagina, int tamano) throws SQLException {
        int offset = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<Curso> cursos = new ArrayList<>();
        int totalElementos = 0;
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement conteo = connection.prepareStatement("SELECT COUNT(*) FROM Curso WHERE estado = ?")) {
            conteo.setString(1, estado);
            try (ResultSet resultado = conteo.executeQuery()) {
                if (resultado.next()) {
                    totalElementos = resultado.getInt(1);
                }
            }
            try (PreparedStatement consulta = connection.prepareStatement("SELECT * FROM Curso WHERE estado = ? ORDER BY idCurso LIMIT ? OFFSET ?")) {
                consulta.setString(1, estado);
                consulta.setInt(2, tamano);
                consulta.setInt(3, offset);
                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        cursos.add(crearCurso(resultado));
                    }
                }
            }
        }
        return new PaginaResponse<>(cursos, pagina, tamano, totalElementos);
    }
    public List<Curso> cursosDeUsuarios(int id, boolean esEstudiante) throws SQLException {
        List<Curso> cursoResponses = new ArrayList<>();

        String sqlSolicitarCursos;

        if (esEstudiante){
            sqlSolicitarCursos = "SELECT * FROM Estudiante estudiate JOIN AsignacionEstudiantes asignacion ON estudiate.idEstudiante = asignacion.idEstudiante JOIN Seccion seccion ON asignacion.idSeccion = seccion.idSeccion JOIN CurriculumCursosGrado curriculum ON seccion.idGrado = curriculum.idGrado JOIN Curso curso ON curriculum.idCurso = curso.idCurso WHERE estudiate.idEstudiante = ?";
        } else {
            sqlSolicitarCursos = "SELECT * FROM Curso curso JOIN AsignacionMaestrosSeccion asignacion ON curso.idCurso = asignacion.idCurso where idMaestro = ?";
        }

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarCursos)) {
            preparedStatement.setInt(1, id);
             ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cursoResponses.add(crearCurso(resultSet));
            }
        }

        return cursoResponses;
    }

    public PaginaResponse<Curso> cursosDeUsuariosPaginados(int id, boolean esEstudiante, int pagina, int tamano) throws SQLException {
        int offset = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<Curso> cursos = new ArrayList<>();
        int totalElementos = 0;
        String sqlConteo;
        String sqlCursos;

        if (esEstudiante) {
            sqlConteo = "SELECT COUNT(DISTINCT curso.idCurso) FROM Estudiante estudiante JOIN AsignacionEstudiantes asignacion ON estudiante.idEstudiante = asignacion.idEstudiante JOIN Seccion seccion ON asignacion.idSeccion = seccion.idSeccion JOIN CurriculumCursosGrado curriculum ON seccion.idGrado = curriculum.idGrado JOIN Curso curso ON curriculum.idCurso = curso.idCurso WHERE estudiante.idEstudiante = ?";
            sqlCursos = "SELECT DISTINCT curso.* FROM Estudiante estudiante JOIN AsignacionEstudiantes asignacion ON estudiante.idEstudiante = asignacion.idEstudiante JOIN Seccion seccion ON asignacion.idSeccion = seccion.idSeccion JOIN CurriculumCursosGrado curriculum ON seccion.idGrado = curriculum.idGrado JOIN Curso curso ON curriculum.idCurso = curso.idCurso WHERE estudiante.idEstudiante = ? ORDER BY curso.idCurso LIMIT ? OFFSET ?";
        } else {
            sqlConteo = "SELECT COUNT(DISTINCT curso.idCurso) FROM Curso curso JOIN AsignacionMaestrosSeccion asignacion ON curso.idCurso = asignacion.idCurso WHERE asignacion.idMaestro = ?";
            sqlCursos = "SELECT DISTINCT curso.* FROM Curso curso JOIN AsignacionMaestrosSeccion asignacion ON curso.idCurso = asignacion.idCurso WHERE asignacion.idMaestro = ? ORDER BY curso.idCurso LIMIT ? OFFSET ?";
        }

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement conteo = connection.prepareStatement(sqlConteo)) {
            conteo.setInt(1, id);
            try (ResultSet resultado = conteo.executeQuery()) {
                if (resultado.next()) {
                    totalElementos = resultado.getInt(1);
                }
            }
            try (PreparedStatement consulta = connection.prepareStatement(sqlCursos)) {
                consulta.setInt(1, id);
                consulta.setInt(2, tamano);
                consulta.setInt(3, offset);
                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        cursos.add(crearCurso(resultado));
                    }
                }
            }
        }
        return new PaginaResponse<>(cursos, pagina, tamano, totalElementos);
    }

    public Optional<Curso> obtenerCurso(int idCurso) throws SQLException{
        Optional<Curso> curso = Optional.empty();
        String sqlSolicitarCursos = "SELECT * FROM Curso WHERE idCurso = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarCursos)){
            preparedStatement.setInt(1,idCurso);
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

    public void activarDesactivarCurso(int idCurso) throws SQLException{
        try (Connection connection = ConnectionDB.getConexion()){
            Estado estado;

            String sqlSolicitarEstado = "SELECT estado FROM Curso WHERE idCurso = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarEstado)){
                preparedStatement.setInt(1,idCurso);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                estado = Estado.valueOf(resultSet.getString("estado"));
            }
            if (estado.equals(Estado.ACTIVO)){
                estado = Estado.INACTIVO;
            }else {
                estado = Estado.ACTIVO;
            }
            String sqlcambiarEstado = "UPDATE Curso SET estado = ? WHERE  idCurso = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlcambiarEstado)){
                preparedStatement.setString(1,estado.name());
                preparedStatement.setInt(2, idCurso);
                preparedStatement.executeUpdate();
            }

        }
    }

    public void actualizarCurso(int idCurso, String nombreActualizado) throws SQLException{
        String sql = "UPDATE Curso SET nombreCurso = ? WHERE idCurso = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,nombreActualizado);
            preparedStatement.setInt(2,idCurso);
            preparedStatement.executeUpdate();
        }
    }

    private Curso crearCurso(ResultSet resultSet) throws SQLException{
        int idCurso = resultSet.getInt("idCurso");
        String nombreCurso = resultSet.getString("nombreCurso");
        return new Curso(idCurso,nombreCurso);
    }

}
