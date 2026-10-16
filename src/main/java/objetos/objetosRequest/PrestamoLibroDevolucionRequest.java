package objetos.objetosRequest;

public class PrestamoLibroDevolucionRequest {
    private String fechaDevolucion;
    private String idPrestamoLibro;

    public PrestamoLibroDevolucionRequest(String fechaDevolucion, String idPrestamoLibro) {
        this.fechaDevolucion = fechaDevolucion;
        this.idPrestamoLibro = idPrestamoLibro;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public String getIdPrestamoLibro() {
        return idPrestamoLibro;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setIdPrestamoLibro(String idPrestamoLibro) {
        this.idPrestamoLibro = idPrestamoLibro;
    }
}
