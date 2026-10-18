package clasesAuxiliares;

import objetos.objetosDB.CarreraDB;
import objetos.objetosResponse.Carrera;
import objetos.objetosResponse.CarreraActualizarRequest;

import java.sql.SQLException;
import java.util.List;

public class CarreraServicio {
    public List<Carrera> catalogo() throws SQLException{
        CarreraDB carreraDB = new CarreraDB();
        return carreraDB.todosLasCarreras();
    }

    public void nuevaCarrera(String nombreCarrera)throws SQLException{
        CarreraDB carreraDB = new CarreraDB();
        carreraDB.ingresarNuevaCarrera(nombreCarrera);
    }

    public void actualizarCarrera(CarreraActualizarRequest carrera)throws SQLException{
        CarreraDB carreraDB = new CarreraDB();
        carreraDB.actualizarCarrera(carrera);
    }

    public void activarDesactivarCarrera(int idCarrera) throws SQLException{
        CarreraDB carreraDB = new CarreraDB();
        carreraDB.activarDesactivarCarrera(idCarrera);
    }

    public Carrera obtenerLibro(int idCarrera)throws SQLException{
        CarreraDB carreraDB = new CarreraDB();
        return carreraDB.obtenerLibro(idCarrera).get();
    }
}
