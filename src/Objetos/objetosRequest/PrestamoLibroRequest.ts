export class PrestamoLibroRequest {
  idLibro!: string;
  idEstudiante!: string;
  costoDia!: string;
  fechaPrestamo!: string;
  fechaEstimadaDevolucion!: string;

  constructor(idLibro: string, idEstudiante: string, costoDia: string, fechaPrestamo: string, fechaEstimadaDevolucion: string) {
    this.idLibro = idLibro;
    this.idEstudiante = idEstudiante;
    this.costoDia = costoDia;
    this.fechaPrestamo = fechaPrestamo;
    this.fechaEstimadaDevolucion = fechaEstimadaDevolucion;
  }
}
