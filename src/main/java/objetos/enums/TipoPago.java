package objetos.enums;

public enum TipoPago {
    COLEGIATURA("Colegiatura"),
    ACTIVIDAD_EXTRACURRICULAR("Activad extracurricular"),
    INSCRIPCIÓN("Inscripción"),
    MULTA("Multa");

    private final String descripcion;

    TipoPago(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }

}
