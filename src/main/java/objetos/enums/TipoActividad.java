package objetos.enums;

public enum TipoActividad {
    PARCIAL("Parcial"),
    ACTIVIDAD("Actividad"),
    ZONA("Zona"),
    EXAMEN_FINAL("Examen Final");

    private final String descripcion;

    TipoActividad(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
}
