import { Estado } from '../enums/Estado';

export class Libro {
  idLibro!: number;
  estado!: Estado;
  autor!: string;
  anio!: number;
  titulo!: string;
  editorial!: string;
  cantidad!: number;

  constructor(idLibro: number, estado: Estado, autor: string, anio: number, titulo: string, editorial: string, cantidad: number) {
    this.idLibro = idLibro;
    this.estado = estado;
    this.autor = autor;
    this.anio = anio;
    this.titulo = titulo;
    this.editorial = editorial;
    this.cantidad = cantidad;
  }
}
