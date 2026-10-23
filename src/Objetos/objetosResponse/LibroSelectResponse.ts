export class LibroSelectResponse {
  idLibro!: number;
  titulo!: string;

  constructor(idLibro: number, titulo: string) {
    this.idLibro = idLibro;
    this.titulo = titulo;
  }
}
