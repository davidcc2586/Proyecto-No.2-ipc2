package objetos.objetosClases;

import objetos.enums.EstadoCarrera;

public class Carrera {
    private int idCarrera;
    private String nombreCarrera;
    private EstadoCarrera estadoCarrera;

    public Carrera(int idCarrera, String nombreCarrera, EstadoCarrera estadoCarrera) {
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
        this.estadoCarrera = estadoCarrera;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public EstadoCarrera getEstadoCarrera() {
        return estadoCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public void setEstadoCarrera(EstadoCarrera estadoCarrera) {
        this.estadoCarrera = estadoCarrera;
    }
}
