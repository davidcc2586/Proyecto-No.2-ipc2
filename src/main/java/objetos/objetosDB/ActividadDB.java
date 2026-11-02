package objetos.objetosDB;

import objetos.enums.Estado;
import objetos.enums.TipoActividad;
import objetos.objetosRequest.ActividadRequest;
import objetos.objetosResponse.Actividad;
import objetos.objetosResponse.Estudiante;
import objetos.objetosResponse.PaginaResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadDB {

    public boolean actividadEnCicloActivo(int idActividad) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Actividad actividad JOIN Seccion seccion ON seccion.idSeccion = actividad.idSeccion JOIN ConfiguracionColegio configuracion ON configuracion.anioLectivo = seccion.anio WHERE actividad.idActividad = ? AND configuracion.estado = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idActividad);
            preparedStatement.setString(2, Estado.ACTIVO.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }
    }

    public double ponderacionActividad(int idActividad) throws SQLException {
        String sql = "SELECT ponderacion FROM Actividad WHERE idActividad = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idActividad);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("ponderacion");
                }
            }
        }
        throw new SQLException("No se encontró la actividad.");
    }

    public List<Actividad> actividadesDelCursoSeccion(int idCurso, int idSeccion) throws SQLException {

        List<Actividad> actividades = new ArrayList<>();
        String sqlSolicitarActividades = "SELECT * FROM Actividad WHERE idCurso = ? AND idSeccion = ?";

        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSolicitarActividades)) {
            preparedStatement.setInt(1, idCurso);
            preparedStatement.setInt(2, idSeccion);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                actividades.add(crearActividad(resultSet));
            }
            resultSet.close();
        }

        return actividades;
    }

    public PaginaResponse<Actividad> actividadesDelCursoSeccionPaginadas(int idCurso, int idSeccion, int pagina, int tamano) throws SQLException {
        int offset = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<Actividad> actividades = new ArrayList<>();
        int totalElementos = 0;
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement conteo = connection.prepareStatement("SELECT COUNT(*) FROM Actividad WHERE idCurso = ? AND idSeccion = ?")) {
            conteo.setInt(1, idCurso);
            conteo.setInt(2, idSeccion);
            try (ResultSet resultado = conteo.executeQuery()) {
                if (resultado.next()) {
                    totalElementos = resultado.getInt(1);
                }
            }
            try (PreparedStatement consulta = connection.prepareStatement("SELECT * FROM Actividad WHERE idCurso = ? AND idSeccion = ? ORDER BY idActividad LIMIT ? OFFSET ?")) {
                consulta.setInt(1, idCurso);
                consulta.setInt(2, idSeccion);
                consulta.setInt(3, tamano);
                consulta.setInt(4, offset);
                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        actividades.add(crearActividad(resultado));
                    }
                }
            }
        }
        return new PaginaResponse<>(actividades, pagina, tamano, totalElementos);
    }
    public void ingresarNuevaActividadParaCursoSeccion(ActividadRequest actividadRequest) throws SQLException{
        try (Connection connection = ConnectionDB.getConexion()) {

            try {
                connection.setAutoCommit(false);

                int idActividad;
                String sqlIngresarNuevaActividad = "INSERT INTO Actividad(idseccion, idcurso, nombreactividad, ponderacion, tipo) VALUES(?,?,?,?,?)";
                try ( PreparedStatement preparedStatement = connection.prepareStatement(sqlIngresarNuevaActividad, Statement.RETURN_GENERATED_KEYS)){
                    preparedStatement.setInt(1, Integer.parseInt(actividadRequest.getIdSeccion()));
                    preparedStatement.setInt(2, Integer.parseInt(actividadRequest.getIdCurso()));
                    preparedStatement.setString(3, actividadRequest.getNombreActividad());
                    preparedStatement.setDouble(4, Double.parseDouble(actividadRequest.getPonderacion()));
                    preparedStatement.setString(5, actividadRequest.getTipoActividad());
                    preparedStatement.executeUpdate();
                    ResultSet resultSet = preparedStatement.getGeneratedKeys();
                    if (resultSet.next()) {
                        idActividad = resultSet.getInt(1);
                    }else {
                        throw new SQLException();
                    }
                }

                List<Integer> idEstudiantesSeccion = idEstudiantesSeccion(Integer.parseInt(actividadRequest.getIdSeccion()));
                String sqlCrearNotasEstudiantes = "INSERT INTO NotaActividad(idActividad, idEstudiante) VALUES (?,?)";
                try ( PreparedStatement preparedStatement = connection.prepareStatement(sqlCrearNotasEstudiantes);){
                    preparedStatement.setInt(1, idActividad);
                    for (int idEstudiante : idEstudiantesSeccion){
                        preparedStatement.setInt(2,idEstudiante);
                        preparedStatement.executeUpdate();
                    }
                }

                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
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

    private Actividad crearActividad(ResultSet resultSet) throws SQLException{
        int idActividad = resultSet.getInt("idActividad");
        int idSeccion = resultSet.getInt("idSeccion");
        int idCurso = resultSet.getInt("idCurso");
        String nombreActividad = resultSet.getString("nombreActividad");
        double ponderacion = resultSet.getDouble("ponderacion");
        TipoActividad tipoActividad = TipoActividad.valueOf(resultSet.getString("tipo"));
        return new Actividad(idActividad,idSeccion,idCurso,nombreActividad,ponderacion,tipoActividad);
    }
}
