export class LibroRequest {
  autor!: string;
  anio!: string;
  titulo!: string;
  editorial!: string;
  cantidad!: string;

  constructor(autor: string, anio: string, titulo: string, editorial: string, cantidad: string) {
    this.autor = autor;
    this.anio = anio;
    this.titulo = titulo;
    this.editorial = editorial;
    this.cantidad = cantidad;
  }
}
