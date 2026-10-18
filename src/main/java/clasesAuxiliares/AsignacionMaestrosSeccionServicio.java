package clasesAuxiliares;


import objetos.objetosDB.AsignacionMaestrosSeccionDB;
import objetos.objetosRequest.AsignarMaestroRequest;

import java.sql.SQLException;

public class AsignacionMaestrosSeccionServicio {



    public void  asignarMaestro(AsignarMaestroRequest asignacion)throws SQLException{
        AsignacionMaestrosSeccionDB asignacionMaestrosSeccionDB = new AsignacionMaestrosSeccionDB();
        asignacionMaestrosSeccionDB.ingresarNuevaAsignacionMaestroSeccion(asignacion);
    }

}
