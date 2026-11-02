package objetos.objetosRequest;

public class PagoRequest {
    private String idEstudiante;
    private String idSecretario;
    private String tipoPago;
    private String cantidad;
    private String descripcion;
    private String fecha;
    private String nombreConsumidor = "CONSUMIDOR FINAL";
    private String nit = "CF";

    public PagoRequest(){

    }

    public PagoRequest(String idEstudiante, String idSecretario, String tipoPago, String cantidad, String descripcion, String fecha) {
        this.idEstudiante = idEstudiante;
        this.idSecretario = idSecretario;
        this.tipoPago = tipoPago;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public PagoRequest(String idEstudiante, String idSecretario, String tipoPago, String cantidad, String descripcion, String fecha, String nombreConsumidor, String nit) {
        this(idEstudiante, idSecretario, tipoPago, cantidad, descripcion, fecha);
        this.nombreConsumidor = nombreConsumidor;
        this.nit = nit;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public String getIdSecretario() {
        return idSecretario;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombreConsumidor() {
        return nombreConsumidor;
    }

    public String getNit() {
        return nit;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setIdSecretario(String idSecretario) {
        this.idSecretario = idSecretario;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNombreConsumidor(String nombreConsumidor) {
        this.nombreConsumidor = nombreConsumidor;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
}
