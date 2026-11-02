package objetos.objetosResponse;

import objetos.enums.EstadoCarrera;

public class CarreraActualizarRequest {
    private String idCarrera;
    private String nombreCarrera;

    public CarreraActualizarRequest(){

    }

    public CarreraActualizarRequest(String idCarrera, String nombreCarrera) {
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
    }

    public String getIdCarrera() {
        return idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setIdCarrera(String idCarrera) {
        this.idCarrera = idCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }
}
