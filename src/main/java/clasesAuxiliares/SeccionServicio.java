package clasesAuxiliares;

import objetos.exceptions.ValidacionException;
import objetos.objetosDB.SeccionDB;
import objetos.objetosRequest.SeccionActualizarRequest;
import objetos.objetosRequest.SeccionRequest;
import objetos.objetosResponse.Seccion;
import objetos.objetosResponse.PaginaResponse;

import java.sql.SQLException;
import java.util.List;

public class SeccionServicio {

    public List<Seccion> seccionesGrado(int idGrado) throws SQLException{
        SeccionDB seccionDB = new SeccionDB();
        return seccionDB.seccionesGrado(idGrado);
    }

    public PaginaResponse<Seccion> seccionesGradoPaginadas(int idGrado, int pagina, int tamano) throws SQLException {
        SeccionDB seccionDB = new SeccionDB();
        return seccionDB.seccionesGradoPaginadas(idGrado, pagina, tamano);
    }
    public void insertarNuevaSeccion(SeccionRequest seccion) throws SQLException,ValidacionException{
        SeccionDB seccionDB = new SeccionDB();
        if (seccionDB.existeSeccion(seccion)){
            throw new ValidacionException("La sección ingresada ya existe");
        }
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
