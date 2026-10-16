package objetos.enums;

public enum Estado {
    ACTIVO("Activo"),
    INACTIVO("Inactivo");

    private final String descripcion;

    Estado(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getdescripcion() {
        return descripcion;
    }
}
