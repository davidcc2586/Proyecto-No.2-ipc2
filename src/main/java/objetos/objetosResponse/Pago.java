package objetos.objetosResponse;

import objetos.enums.TipoPago;

import java.time.LocalDate;

public class Pago {
    private int idPago;
    private int idEstudiante;
    private int idSecretario;
    private TipoPago tipoPago;
    private double cantidad;
    private String descripcion;
    private LocalDate fecha;

    public Pago(int idPago, int idEstudiante, int idSecretario, TipoPago tipoPago, double cantidad, String descripcion, LocalDate fecha) {
        this.idPago = idPago;
        this.idEstudiante = idEstudiante;
        this.idSecretario = idSecretario;
        this.tipoPago = tipoPago;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getIdPago() {
        return idPago;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public int getIdSecretario() {
        return idSecretario;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public double getCantidad() {
        return cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setIdSecretario(int idSecretario) {
        this.idSecretario = idSecretario;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
