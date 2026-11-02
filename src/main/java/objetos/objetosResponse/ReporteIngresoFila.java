package objetos.objetosResponse;

public class ReporteIngresoFila {
    private String concepto;
    private int cantidadPagos;
    private double total;

    public ReporteIngresoFila(String concepto, int cantidadPagos, double total) {
        this.concepto = concepto;
        this.cantidadPagos = cantidadPagos;
        this.total = total;
    }

    public String getConcepto() { return concepto; }
    public int getCantidadPagos() { return cantidadPagos; }
    public double getTotal() { return total; }
}
