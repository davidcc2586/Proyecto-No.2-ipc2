package clasesAuxiliares;

import objetos.objetosDB.SeccionDB;
import objetos.objetosRequest.SeccionActualizarRequest;
import objetos.objetosRequest.SeccionRequest;
import objetos.objetosResponse.Seccion;

import java.sql.SQLException;
import java.util.List;

public class SeccionServicio {

    public List<Seccion> seccionesGrado(int idGrado) throws SQLException{
        SeccionDB seccionDB = new SeccionDB();
        return seccionDB.seccionesGrado(idGrado);
    }
    public void insertarNuevaSeccion(SeccionRequest seccion) throws SQLException{
        SeccionDB seccionDB = new SeccionDB();
        seccionDB.insertarNuevaSeccion(seccion);
    }
    public void actualizarSeccion(SeccionActualizarRequest seccion) throws SQLException{
        SeccionDB seccionDB = new SeccionDB();
        seccionDB.actualizarSeccion(seccion);
    }

    public Seccion obtenerSeccion(int idSeccion) throws SQLException{
        SeccionDB seccionDB = new SeccionDB();
        return seccionDB.obtenerSeccion(idSeccion).get();
    }
}
