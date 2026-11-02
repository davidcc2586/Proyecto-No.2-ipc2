package objetos.enums;

public enum EstadoFinalPrestamoLibro {
    MOROSO("Moroso"),
    PUNTUAL("Puntual");

    private final String descripcion;

    EstadoFinalPrestamoLibro(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
}
