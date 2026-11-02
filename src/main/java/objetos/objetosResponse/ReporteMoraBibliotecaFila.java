package objetos.objetosResponse;

public class ReporteMoraBibliotecaFila {
    private String libro;
    private String estudiante;
    private String cui;
    private String fechaEstimada;
    private int diasRetraso;
    private double costoDia;
    private double recargo;

    public ReporteMoraBibliotecaFila(String libro, String estudiante, String cui, String fechaEstimada,
                                     int diasRetraso, double costoDia, double recargo) {
        this.libro = libro;
        this.estudiante = estudiante;
        this.cui = cui;
        this.fechaEstimada = fechaEstimada;
        this.diasRetraso = diasRetraso;
        this.costoDia = costoDia;
        this.recargo = recargo;
    }

    public String getLibro() { return libro; }
    public String getEstudiante() { return estudiante; }
    public String getCui() { return cui; }
    public String getFechaEstimada() { return fechaEstimada; }
    public int getDiasRetraso() { return diasRetraso; }
    public double getCostoDia() { return costoDia; }
    public double getRecargo() { return recargo; }
}
