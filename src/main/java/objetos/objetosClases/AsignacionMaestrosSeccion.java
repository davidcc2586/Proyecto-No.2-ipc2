package objetos.objetosClases;

public class AsignacionMaestrosSeccion {
    private int idSeccion;
    private int idMaestro;
    private int idCurso;

    public AsignacionMaestrosSeccion(int idSeccion, int idMaestro, int idCurso) {
        this.idSeccion = idSeccion;
        this.idMaestro = idMaestro;
        this.idCurso = idCurso;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public int getIdMaestro() {
        return idMaestro;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public void setIdMaestro(int idMaestro) {
        this.idMaestro = idMaestro;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }
}
