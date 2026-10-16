package objetos.objetosResponse;

public class NotaActividad {
    private int idActividad;
    private int idEstudiante;
    private double punteoObtenido;

    public NotaActividad(int idActividad, int idEstudiante, double punteoObtenido) {
        this.idActividad = idActividad;
        this.idEstudiante = idEstudiante;
        this.punteoObtenido = punteoObtenido;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public double getPunteoObtenido() {
        return punteoObtenido;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setPunteoObtenido(double punteoObtenido) {
        this.punteoObtenido = punteoObtenido;
    }
}
