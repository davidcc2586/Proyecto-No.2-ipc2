package objetos.objetosResponse;

import objetos.enums.Estado;

public class Libro {
    private int idLibro;
    private Estado estado;
    private String autor;
    private int anio;
    private String titulo;
    private String editorial;
    private int cantidad;

    public Libro(int idLibro, Estado estado, String autor, int anio, String titulo, String editorial, int cantidad) {
        this.idLibro = idLibro;
        this.estado = estado;
        this.autor = autor;
        this.anio = anio;
        this.titulo = titulo;
        this.editorial = editorial;
        this.cantidad = cantidad;
    }

    public Estado getEstado() {
        return estado;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnio() {
        return anio;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
