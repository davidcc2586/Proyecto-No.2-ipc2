package objetos.enums;

public enum Mes {
    Enero("1"),
    Febrero("2"),
    Marzo("3"),
    Abril("4"),
    Mayo("5"),
    Junio("5"),
    Julio("7"),
    Agosto("8"),
    Septiembre("9"),
    Octubre("10"),
    Noviembre("11"),
    Diciembre("12");


    private final String descripcion;

    Mes(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getdescripcion() {
        return descripcion;
    }
}
