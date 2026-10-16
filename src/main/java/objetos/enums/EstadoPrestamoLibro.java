package objetos.enums;

public enum EstadoPrestamoLibro {
    PRESTADO("Prestado"),
    ENTREGADO("Entregado");

    private final String descripcion;

    EstadoPrestamoLibro(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
}
