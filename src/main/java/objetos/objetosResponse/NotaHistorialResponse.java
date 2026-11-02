package objetos.objetosResponse;

public class NotaHistorialResponse {
    private int anioLectivo;
    private int idCurso;
    private String nombreCurso;
    private int idActividad;
    private String nombreActividad;
    private String tipoActividad;
    private double ponderacion;
    private double punteoObtenido;

    public NotaHistorialResponse() {
    }

    public NotaHistorialResponse(int anioLectivo, int idCurso, String nombreCurso, int idActividad, String nombreActividad, String tipoActividad, double ponderacion, double punteoObtenido) {
        this.anioLectivo = anioLectivo;
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.tipoActividad = tipoActividad;
        this.ponderacion = ponderacion;
        this.punteoObtenido = punteoObtenido;
    }

    public int getAnioLectivo() {
        return anioLectivo;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public double getPonderacion() {
        return ponderacion;
    }

    public double getPunteoObtenido() {
        return punteoObtenido;
    }

    public void setAnioLectivo(int anioLectivo) {
        this.anioLectivo = anioLectivo;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public void setPonderacion(double ponderacion) {
        this.ponderacion = ponderacion;
    }

    public void setPunteoObtenido(double punteoObtenido) {
        this.punteoObtenido = punteoObtenido;
    }
}
