package objetos.enums;

public enum RolUsuario {
    SUPERADMIN("SuperAdmin"),
    ADMIN("Admin"),
    SECRETARIO("Secretario"),
    BIBLIOTECARIO("Bibliotecario"),
    PERSONAL("Personal"),
    MAESTRO("Maestro"),
    ESTUDIANTE("Estudiante");

    private final String descripcion;

    RolUsuario(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
}
