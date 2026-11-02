package clasesAuxiliares;

import objetos.exceptions.ValidacionException;
import objetos.objetosDB.ActividadDB;
import objetos.objetosDB.NotaActividadDB;
import objetos.objetosRequest.ActividadRequest;
import objetos.objetosRequest.NotaRequest;
import objetos.objetosResponse.Actividad;
import objetos.objetosResponse.NotaActividad;
import objetos.objetosResponse.NotaHistorialResponse;
import objetos.objetosResponse.PaginaResponse;

import java.sql.SQLException;
import java.util.List;

public class NotasServicio {

    public void ingresarActividadZona(ActividadRequest actividadRequest) throws SQLException, ValidacionException{

        try {
            double ponderacion = Double.parseDouble(actividadRequest.getPonderacion());
            if (ponderacion < 0){
                throw new ValidacionException("La ponderación de punteo no puede ser negativa");
            }
        } catch (NumberFormatException e) {
            throw new ValidacionException("Ponderación de punteo en invalido");
        }
        ActividadDB actividadDB = new ActividadDB();
        actividadDB.ingresarNuevaActividadParaCursoSeccion(actividadRequest);
    }
    public List<NotaActividad> notasActividad(int idActividad) throws SQLException{
        NotaActividadDB notaActividadDB = new NotaActividadDB();
        return notaActividadDB.notasActividad(idActividad);
    }

    public PaginaResponse<NotaActividad> notasActividadPaginadas(int idActividad, int pagina, int tamano) throws SQLException {
        NotaActividadDB notaActividadDB = new NotaActividadDB();
        return notaActividadDB.notasActividadPaginadas(idActividad, pagina, tamano);
    }

    public List<Actividad> actividades(int idCurso, int idSeccion) throws SQLException{
        ActividadDB actividadDB = new ActividadDB();
        return actividadDB.actividadesDelCursoSeccion(idCurso,idSeccion);
    }

    public PaginaResponse<Actividad> actividadesPaginadas(int idCurso, int idSeccion, int pagina, int tamano) throws SQLException {
        ActividadDB actividadDB = new ActividadDB();
        return actividadDB.actividadesDelCursoSeccionPaginadas(idCurso, idSeccion, pagina, tamano);
    }

    public void modificarNota(NotaRequest notaRequest) throws SQLException, ValidacionException{
        int idActividad = Integer.parseInt(notaRequest.getIdActividad());
        try {
            double ponderacion = Double.parseDouble(notaRequest.getPunteoObtenido());
            if (ponderacion < 0){
                throw new ValidacionException("La ponderación de punteo no puede ser negativa");
            }
        } catch (NumberFormatException e) {
            throw new ValidacionException("Ponderación de punteo en invalido");
        }
        ActividadDB actividadDB = new ActividadDB();
        if (!actividadDB.actividadEnCicloActivo(idActividad)) {
            throw new ValidacionException("Solo se pueden ingresar o modificar notas durante el ciclo escolar activo.");
        }
        if (Double.parseDouble(notaRequest.getPunteoObtenido()) > actividadDB.ponderacionActividad(idActividad)) {
            throw new ValidacionException("El punteo no puede superar la ponderación de la actividad.");
        }
        NotaActividadDB notaActividadDB = new NotaActividadDB();
        notaActividadDB.modificarNota(notaRequest);
    }

    public List<NotaHistorialResponse> historialEstudiante(int idEstudiante) throws SQLException {
        NotaActividadDB notaActividadDB = new NotaActividadDB();
        return notaActividadDB.historialEstudiante(idEstudiante);
    }
}
