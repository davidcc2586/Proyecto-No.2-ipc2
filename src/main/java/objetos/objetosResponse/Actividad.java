package objetos.objetosResponse;

import objetos.enums.TipoActividad;

public class Actividad {
    private int idActividad;
    private int idSeccion;
    private int idCurso;
    private String nombreActividad;
    private double ponderacion;
    private TipoActividad tipoActividad;

    public Actividad(){

    }

    public Actividad(int idActividad, int idSeccion, int idCurso, String nombreActividad, double ponderacion, TipoActividad tipoActividad) {
        this.idActividad = idActividad;
        this.idSeccion = idSeccion;
        this.idCurso = idCurso;
        this.nombreActividad = nombreActividad;
        this.ponderacion = ponderacion;
        this.tipoActividad = tipoActividad;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public double getPonderacion() {
        return ponderacion;
    }

    public TipoActividad getTipoActividad() {
        return tipoActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public void setPonderacion(double ponderacion) {
        this.ponderacion = ponderacion;
    }

    public void setTipoActividad(TipoActividad tipoActividad) {
        this.tipoActividad = tipoActividad;
    }
}
