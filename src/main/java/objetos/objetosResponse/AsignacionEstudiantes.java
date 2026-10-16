package objetos.objetosResponse;

public class AsignacionEstudiantes {
    private int idSeccion;
    private int isEstudiante;

    public AsignacionEstudiantes(int idSeccion, int isEstudiante) {
        this.idSeccion = idSeccion;
        this.isEstudiante = isEstudiante;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public int getIsEstudiante() {
        return isEstudiante;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public void setIsEstudiante(int isEstudiante) {
        this.isEstudiante = isEstudiante;
    }
}
