package objetos.objetosClases;

public class Curso {
    private int idCurso;
    private int idGrado;
    private String nombreCurso;

    public Curso(int idCurso, int idGrado, String nombreCurso) {
        this.idCurso = idCurso;
        this.idGrado = idGrado;
        this.nombreCurso = nombreCurso;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }
}
