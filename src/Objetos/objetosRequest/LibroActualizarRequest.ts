export class LibroActualizarRequest {
  idLibro!: string;
  autor!: string;
  anio!: string;
  titulo!: string;
  editorial!: string;
  cantidad!: string;

  constructor(idLibro: string, autor: string, anio: string, titulo: string, editorial: string, cantidad: string) {
    this.idLibro = idLibro;
    this.autor = autor;
    this.anio = anio;
    this.titulo = titulo;
    this.editorial = editorial;
    this.cantidad = cantidad;
  }
}
