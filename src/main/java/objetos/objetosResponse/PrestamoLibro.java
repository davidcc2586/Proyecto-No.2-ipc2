package objetos.objetosResponse;

import objetos.enums.EstadoFinalPrestamoLibro;
import objetos.enums.EstadoPrestamoLibro;

import java.time.LocalDate;

public class PrestamoLibro {
    private int idPrestamoLibro;
    private int idLibro;
    private int idEstudiante;
    private EstadoPrestamoLibro estadoPrestamoLibro;
    private EstadoFinalPrestamoLibro estadoFinalPrestamoLibro;
    private double costoDia;
    private LocalDate fechaPrestamo;
    private LocalDate fechaEstimadaDevolucion;
    private LocalDate fechaDevolucion;
    private double multa;

    public PrestamoLibro(int idPrestamoLibro, int idLibro, int idEstudiante, EstadoPrestamoLibro estadoPrestamoLibro, EstadoFinalPrestamoLibro estadoFinalPrestamoLibro, double costoDia, LocalDate fechaPrestamo, LocalDate fechaEstimadaDevolucion, LocalDate fechaDevolucion, Double multa) {
        this.idPrestamoLibro = idPrestamoLibro;
        this.idLibro = idLibro;
        this.idEstudiante = idEstudiante;
        this.estadoPrestamoLibro = estadoPrestamoLibro;
        this.estadoFinalPrestamoLibro = estadoFinalPrestamoLibro;
        this.costoDia = costoDia;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaEstimadaDevolucion = fechaEstimadaDevolucion;
        this.fechaDevolucion = fechaDevolucion;
        this.multa = multa;
    }

    public PrestamoLibro(int idPrestamoLibro, int idLibro, int idEstudiante, EstadoPrestamoLibro estadoPrestamoLibro, EstadoFinalPrestamoLibro estadoFinalPrestamoLibro, double costoDia, LocalDate fechaPrestamo, LocalDate fechaEstimadaDevolucion, Double multa) {
        this.idPrestamoLibro = idPrestamoLibro;
        this.idLibro = idLibro;
        this.idEstudiante = idEstudiante;
        this.estadoPrestamoLibro = estadoPrestamoLibro;
        this.estadoFinalPrestamoLibro = estadoFinalPrestamoLibro;
        this.costoDia = costoDia;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaEstimadaDevolucion = fechaEstimadaDevolucion;
        this.multa = multa;
    }

    public int getIdPrestamoLibro() {
        return idPrestamoLibro;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public EstadoPrestamoLibro getEstadoPrestamoLibro() {
        return estadoPrestamoLibro;
    }

    public EstadoFinalPrestamoLibro getEstadoFinalPrestamoLibro() {
        return estadoFinalPrestamoLibro;
    }

    public double getCostoDia() {
        return costoDia;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaEstimadaDevolucion() {
        return fechaEstimadaDevolucion;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public double getMulta() {
        return multa;
    }

    public void setIdPrestamoLibro(int idPrestamoLibro) {
        this.idPrestamoLibro = idPrestamoLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setEstadoPrestamoLibro(EstadoPrestamoLibro estadoPrestamoLibro) {
        this.estadoPrestamoLibro = estadoPrestamoLibro;
    }

    public void setEstadoFinalPrestamoLibro(EstadoFinalPrestamoLibro estadoFinalPrestamoLibro) {
        this.estadoFinalPrestamoLibro = estadoFinalPrestamoLibro;
    }

    public void setCostoDia(double costoDia) {
        this.costoDia = costoDia;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaEstimadaDevolucion(LocalDate fechaEstimadaDevolucion) {
        this.fechaEstimadaDevolucion = fechaEstimadaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }
}
