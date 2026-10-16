package objetos.enums;

public enum EstadoEstudiante {
    ACTIVO("Activo"),
    INACTIVO("Inactivo"),
    GRADUADO("Graduado");

    private final String descripcion;

    EstadoEstudiante(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
}
