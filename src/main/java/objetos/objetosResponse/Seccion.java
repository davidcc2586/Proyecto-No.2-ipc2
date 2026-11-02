package objetos.objetosResponse;

public class Seccion {
    private int idSeccion;
    private int idGrado;
    private int anio;
    private char seccion;

    public Seccion(int idSeccion, int idGrado, int anio, char seccion) {
        this.idSeccion = idSeccion;
        this.idGrado = idGrado;
        this.anio = anio;
        this.seccion = seccion;
    }

    public Seccion(){

    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public int getAnio() {
        return anio;
    }

    public char getSeccion() {
        return seccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setSeccion(char seccion) {
        this.seccion = seccion;
    }
}
