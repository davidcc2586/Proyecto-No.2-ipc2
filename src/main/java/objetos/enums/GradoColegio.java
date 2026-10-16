package objetos.enums;

public enum GradoColegio {
    PRE_PRIMARIA ("Pre-Primaria"),
    PRIMARIA("Primaria"),
    BASICO("Basico"),
    DIVERSIFICADO("Diversificado");

    private final String descripcion;

    GradoColegio(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
}
