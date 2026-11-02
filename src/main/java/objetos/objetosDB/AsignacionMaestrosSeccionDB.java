package objetos.objetosDB;

import objetos.enums.Estado;
import objetos.objetosRequest.AsignarMaestroRequest;
import objetos.objetosResponse.AsignacionMaestrosSeccion;
import objetos.objetosResponse.PaginaResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsignacionMaestrosSeccionDB {

    public void ingresarNuevaAsignacionMaestroSeccion(AsignarMaestroRequest asignacion) throws SQLException {
        String sqlAsignarMaestroASeccion = "INSERT INTO AsignacionMaestrosSeccion(idSeccion, idMaestro, idCurso) VALUES(?,?,?)";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlAsignarMaestroASeccion)){
            preparedStatement.setInt(1, Integer.parseInt(asignacion.getIdSeccion()));
            preparedStatement.setInt(2, Integer.parseInt(asignacion.getIdMaestro()));
            preparedStatement.setInt(3, Integer.parseInt(asignacion.getIdCurso()));
            preparedStatement.executeUpdate();
        }
    }

    public List<AsignacionMaestrosSeccion> asignacionesMaestro(int idMaestro) throws SQLException {
        String sqlAsignaciones = "SELECT * FROM AsignacionMaestrosSeccion asignacion JOIN Seccion seccion ON seccion.idSeccion = asignacion.idSeccion JOIN ConfiguracionColegio configuracion ON configuracion.anioLectivo = seccion.anio WHERE asignacion.idMaestro = ? AND configuracion.estado = 'ACTIVO'";
        List<AsignacionMaestrosSeccion> asignaciones = new ArrayList<>();
        try (Connection connection = ConnectionDB.getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlAsignaciones)){
            preparedStatement.setInt(1, idMaestro);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                asignaciones.add(new AsignacionMaestrosSeccion(resultSet.getInt("idSeccion"),idMaestro, resultSet.getInt("idCurso")));
            }
        }
        return asignaciones;
    }

    public PaginaResponse<AsignacionMaestrosSeccion> asignacionesMaestroPaginadas(int idMaestro, int pagina, int tamano) throws SQLException {
        int desplazamiento = new objetos.Paginacion().calcularDesplazamiento(pagina, tamano);
        List<AsignacionMaestrosSeccion> asignaciones = new ArrayList<>();
        int totalElementos = 0;
        String filtro = " FROM AsignacionMaestrosSeccion asignacion JOIN Seccion seccion ON seccion.idSeccion = asignacion.idSeccion JOIN ConfiguracionColegio configuracion ON configuracion.anioLectivo = seccion.anio WHERE asignacion.idMaestro = ? AND configuracion.estado = ?";
        try (Connection connection = ConnectionDB.getConexion();
             PreparedStatement conteo = connection.prepareStatement("SELECT COUNT(*)" + filtro)) {
            conteo.setInt(1, idMaestro);
            conteo.setString(2, Estado.ACTIVO.name());
            try (ResultSet resultado = conteo.executeQuery()) {
                if (resultado.next()) {
                    totalElementos = resultado.getInt(1);
                }
            }
            try (PreparedStatement consulta = connection.prepareStatement("SELECT *" + filtro + " ORDER BY asignacion.idSeccion, asignacion.idCurso LIMIT ? OFFSET ?")) {
                consulta.setInt(1, idMaestro);
                consulta.setString(2, Estado.ACTIVO.name());
                consulta.setInt(3, tamano);
                consulta.setInt(4, desplazamiento);
                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        asignaciones.add(new AsignacionMaestrosSeccion(resultado.getInt("idSeccion"), idMaestro, resultado.getInt("idCurso")));
                    }
                }
            }
        }
        return new PaginaResponse<>(asignaciones, pagina, tamano, totalElementos);
    }

}
