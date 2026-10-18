package clasesAuxiliares;

import objetos.objetosDB.GradoDB;
import objetos.objetosRequest.GradoActualizarRequest;
import objetos.objetosRequest.GradoRequest;
import objetos.objetosResponse.Grado;
import objetos.objetosResponse.SelectGradosResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradoServicio {

    public List<Grado> catalogo() throws SQLException{
        GradoDB gradoDB = new GradoDB();
        return gradoDB.todosLosGrados();
    }

    public void nuevoGrado(GradoRequest grado) throws SQLException{
        GradoDB gradoDB = new GradoDB();
        gradoDB.ingresarNuevoGrado(grado);
    }

    public void actualizarGrado(GradoActualizarRequest grado) throws SQLException{
        GradoDB gradoDB = new GradoDB();
        gradoDB.actualizarGrado(grado);
    }

    public List<SelectGradosResponse> selectGrados() throws SQLException{
        GradoDB gradoDB = new GradoDB();
        List<Grado> grados =  gradoDB.todosLosGrados();
        List<SelectGradosResponse> selectGrados =  new ArrayList<>();
        for (Grado grado : grados){
            selectGrados.add(new SelectGradosResponse(grado.getIdGrado(), grado.getNombreGrado()));
        }
        return selectGrados;
    }

    public Grado obtenerGrado(int idGrado)throws SQLException{
        GradoDB gradoDB = new GradoDB();
        return gradoDB.obtenerGrado(idGrado).get();
    }
}
