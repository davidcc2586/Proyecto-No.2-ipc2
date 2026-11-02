package objetos.objetosResponse;

public class Factura {
    private int idFactura;
    private int idPago;
    private String nombreConsumidor;
    private String nit;

    public Factura(){

    }

    public Factura(int idFactura, int idPago, String nombreConsumidor, String nit) {
        this.idFactura = idFactura;
        this.idPago = idPago;
        this.nombreConsumidor = nombreConsumidor;
        this.nit = nit;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public int getIdPago() {
        return idPago;
    }

    public String getNombreConsumidor() {
        return nombreConsumidor;
    }

    public String getNit() {
        return nit;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public void setNombreConsumidor(String nombreConsumidor) {
        this.nombreConsumidor = nombreConsumidor;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
}
