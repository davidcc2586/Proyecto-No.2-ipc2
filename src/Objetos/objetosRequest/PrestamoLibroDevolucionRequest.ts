export class PrestamoLibroDevolucionRequest {
  fechaDevolucion!: string;
  idPrestamoLibro!: string;

  constructor(fechaDevolucion: string, idPrestamoLibro: string) {
    this.fechaDevolucion = fechaDevolucion;
    this.idPrestamoLibro = idPrestamoLibro;
  }
}
