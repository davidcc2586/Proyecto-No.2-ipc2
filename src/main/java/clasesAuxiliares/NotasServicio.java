package clasesAuxiliares;

import objetos.exceptions.ValidacionException;
import objetos.objetosDB.ActividadDB;
import objetos.objetosDB.NotaActividadDB;
import objetos.objetosRequest.ActividadRequest;
import objetos.objetosRequest.NotaRequest;
import objetos.objetosResponse.Actividad;
import objetos.objetosResponse.NotaActividad;

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

    public List<Actividad> actividades(int idCurso, int idSeccion) throws SQLException{
        ActividadDB actividadDB = new ActividadDB();
        return actividadDB.actividadesDelCursoSeccion(idCurso,idSeccion);
    }

    public void modificarNota(NotaRequest notaRequest) throws SQLException, ValidacionException{
        try {
            double ponderacion = Double.parseDouble(notaRequest.getPunteoObtenido());
            if (ponderacion < 0){
                throw new ValidacionException("La ponderación de punteo no puede ser negativa");
            }
        } catch (NumberFormatException e) {
            throw new ValidacionException("Ponderación de punteo en invalido");
        }
        NotaActividadDB notaActividadDB = new NotaActividadDB();
        notaActividadDB.modificarNota(notaRequest);
    }
}
