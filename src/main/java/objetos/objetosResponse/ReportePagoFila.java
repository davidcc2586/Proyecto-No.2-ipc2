package objetos.objetosResponse;

public class ReportePagoFila {
    private String fecha;
    private String tipoPago;
    private double cantidad;
    private String descripcion;
    private String factura;

    public ReportePagoFila(String fecha, String tipoPago, double cantidad, String descripcion, String factura) {
        this.fecha = fecha;
        this.tipoPago = tipoPago;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.factura = factura;
    }

    public String getFecha() { return fecha; }
    public String getTipoPago() { return tipoPago; }
    public double getCantidad() { return cantidad; }
    public String getDescripcion() { return descripcion; }
    public String getFactura() { return factura; }
}
