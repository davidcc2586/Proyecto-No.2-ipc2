package objetos.objetosRequest;

public class ActividadRequest {

    private String idSeccion;
    private String idCurso;
    private String nombreActividad;
    private String ponderacion;
    private String tipoActividad;

    public ActividadRequest(String idSeccion, String idCurso, String nombreActividad, String ponderacion, String tipoActividad) {
        this.idSeccion = idSeccion;
        this.idCurso = idCurso;
        this.nombreActividad = nombreActividad;
        this.ponderacion = ponderacion;
        this.tipoActividad = tipoActividad;
    }

    public String getIdSeccion() {
        return idSeccion;
    }

    public String getIdCurso() {
        return idCurso;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public String getPonderacion() {
        return ponderacion;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setIdSeccion(String idSeccion) {
        this.idSeccion = idSeccion;
    }

    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public void setPonderacion(String ponderacion) {
        this.ponderacion = ponderacion;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }
}
