package objetos.objetosRequest;

public class PrestamoLibroRequest {
    private String idLibro;
    private String idEstudiante;
    private String costoDia;
    private String fechaPrestamo;
    private String fechaEstimadaDevolucion;

    public PrestamoLibroRequest(){

    }

    public PrestamoLibroRequest(String idLibro, String idEstudiante, String costoDia, String fechaPrestamo, String fechaEstimadaDevolucion) {
        this.idLibro = idLibro;
        this.idEstudiante = idEstudiante;
        this.costoDia = costoDia;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaEstimadaDevolucion = fechaEstimadaDevolucion;
    }

    public String getIdLibro() {
        return idLibro;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public String getCostoDia() {
        return costoDia;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public String getFechaEstimadaDevolucion() {
        return fechaEstimadaDevolucion;
    }

    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setCostoDia(String costoDia) {
        this.costoDia = costoDia;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaEstimadaDevolucion(String fechaEstimadaDevolucion) {
        this.fechaEstimadaDevolucion = fechaEstimadaDevolucion;
    }
}
