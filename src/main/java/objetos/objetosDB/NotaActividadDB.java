package objetos.objetosDB;

import objetos.objetosRequest.NotaRequest;
import objetos.objetosResponse.Estudiante;
import objetos.objetosResponse.NotaActividad;
import objetos.objetosResponse.PaginaResponse;
import objetos.objetosResponse.NotaHistorialResponse;

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

    public PaginaResponse<NotaActividad> notasActividadPaginadas(int idActividad, int pagina, int tamano) throws SQLException {
        int offset = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<NotaActividad> notas = new ArrayList<>();
        int totalElementos = 0;
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement conteo = connection.prepareStatement("SELECT COUNT(*) FROM NotaActividad WHERE idActividad = ?")) {
            conteo.setInt(1, idActividad);
            try (ResultSet resultado = conteo.executeQuery()) {
                if (resultado.next()) {
                    totalElementos = resultado.getInt(1);
                }
            }
            try (PreparedStatement consulta = connection.prepareStatement("SELECT * FROM NotaActividad WHERE idActividad = ? ORDER BY idEstudiante LIMIT ? OFFSET ?")) {
                consulta.setInt(1, idActividad);
                consulta.setInt(2, tamano);
                consulta.setInt(3, offset);
                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        notas.add(crearNotaActividad(resultado));
                    }
                }
            }
        }
        return new PaginaResponse<>(notas, pagina, tamano, totalElementos);
    }

    public void modificarNota(NotaRequest nota) throws SQLException{
        String sql = "UPDATE NotaActividad na JOIN Actividad a ON a.idActividad = na.idActividad JOIN Seccion s ON s.idSeccion = a.idSeccion JOIN ConfiguracionColegio c ON c.anioLectivo = s.anio SET na.punteoObtenido = ? WHERE na.idEstudiante = ? AND na.idActividad = ? AND c.estado = 'ACTIVO'";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setDouble(1, Double.parseDouble(nota.getPunteoObtenido()));
            preparedStatement.setInt(2, Integer.parseInt(nota.getIdEstudiante()));
            preparedStatement.setInt(3,Integer.parseInt(nota.getIdActividad()));
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("No se actualizó la nota. Verifica el estudiante, la actividad y el ciclo escolar activo.");
            }
        }
    }

    public List<NotaHistorialResponse> historialEstudiante(int idEstudiante) throws SQLException {
        List<NotaHistorialResponse> historial = new ArrayList<>();
        String sql = "SELECT seccion.anio, curso.idCurso, curso.nombreCurso, actividad.idActividad, actividad.nombreActividad, actividad.tipo, actividad.ponderacion, notaActividad.punteoObtenido FROM NotaActividad notaActividad JOIN Actividad actividad ON actividad.idActividad = notaActividad.idActividad JOIN Curso curso ON curso.idCurso = actividad.idCurso JOIN Seccion seccion ON seccion.idSeccion = actividad.idSeccion WHERE notaActividad.idEstudiante = ? ORDER BY seccion.anio DESC, curso.nombreCurso, actividad.idActividad";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idEstudiante);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    historial.add(new NotaHistorialResponse(resultSet.getInt("anio"), resultSet.getInt("idCurso"), resultSet.getString("nombreCurso"), resultSet.getInt("idActividad"), resultSet.getString("nombreActividad"), resultSet.getString("tipo"), resultSet.getDouble("ponderacion"), resultSet.getDouble("punteoObtenido")));
                }
            }
        }
        return historial;
    }

    private NotaActividad crearNotaActividad(ResultSet resultSet) throws SQLException {
        int idActividad = resultSet.getInt("idActividad");
        int idEstudiante = resultSet.getInt("idEstudiante");
        double punteoObtenido = resultSet.getDouble("punteoObtenido");
        return new NotaActividad(idActividad,idEstudiante,punteoObtenido);
    }
}
