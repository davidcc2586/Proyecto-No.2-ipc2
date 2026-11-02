package objetos.objetosResponse;

public class CurriculumCursosGrado {
    private int idGrado;
    private int idCurso;

    public CurriculumCursosGrado(){

    }
    public CurriculumCursosGrado(int idGrado, int idCurso) {
        this.idGrado = idGrado;
        this.idCurso = idCurso;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }
}
