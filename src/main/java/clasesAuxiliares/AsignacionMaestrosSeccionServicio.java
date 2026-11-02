package clasesAuxiliares;

import objetos.objetosDB.AsignacionMaestrosSeccionDB;
import objetos.objetosRequest.AsignarMaestroRequest;
import objetos.objetosResponse.AsignacionMaestrosSeccion;
import objetos.objetosResponse.PaginaResponse;

import java.sql.SQLException;
import java.util.List;

public class AsignacionMaestrosSeccionServicio {



    public void  asignarMaestro(AsignarMaestroRequest asignacion)throws SQLException{
        AsignacionMaestrosSeccionDB asignacionMaestrosSeccionDB = new AsignacionMaestrosSeccionDB();
        asignacionMaestrosSeccionDB.ingresarNuevaAsignacionMaestroSeccion(asignacion);
    }

    public List<AsignacionMaestrosSeccion> asignacionesMaestro(int idMaestro)throws SQLException{
        AsignacionMaestrosSeccionDB asignacionMaestrosSeccionDB = new AsignacionMaestrosSeccionDB();
        return asignacionMaestrosSeccionDB.asignacionesMaestro(idMaestro);
    }

    public PaginaResponse<AsignacionMaestrosSeccion> asignacionesMaestroPaginadas(int idMaestro, int pagina, int tamano) throws SQLException {
        AsignacionMaestrosSeccionDB asignacionMaestrosSeccionDB = new AsignacionMaestrosSeccionDB();
        return asignacionMaestrosSeccionDB.asignacionesMaestroPaginadas(idMaestro, pagina, tamano);
    }

}
