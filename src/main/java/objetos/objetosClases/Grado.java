package objetos.objetosClases;

import objetos.enums.GradoColegio;

public class Grado {
    private int idGrado;
    private GradoColegio gradoColegio;
    private int idCarrera;

    public Grado(int idGrado, GradoColegio gradoColegio) {
        this.idGrado = idGrado;
        this.gradoColegio = gradoColegio;
    }

    public Grado(int idGrado, GradoColegio gradoColegio, int idCarrera) {
        this.idGrado = idGrado;
        this.gradoColegio = gradoColegio;
        this.idCarrera = idCarrera;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public GradoColegio getGradoColegio() {
        return gradoColegio;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public void setGradoColegio(GradoColegio gradoColegio) {
        this.gradoColegio = gradoColegio;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }
}
