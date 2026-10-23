import { EstadoFinalPrestamoLibro } from '../enums/EstadoFinalPrestamoLibro';
import { EstadoPrestamoLibro } from '../enums/EstadoPrestamoLibro';

export class PrestamoLibro {
  idPrestamoLibro!: number;
  idLibro!: number;
  idEstudiante!: number;
  estadoPrestamoLibro!: EstadoPrestamoLibro;
  estadoFinalPrestamoLibro!: EstadoFinalPrestamoLibro;
  costoDia!: number;
  fechaPrestamo!: string;
  fechaEstimadaDevolucion!: string;
  fechaDevolucion!: string | null;
  multa!: number;

  constructor(idPrestamoLibro: number, idLibro: number, idEstudiante: number, estadoPrestamoLibro: EstadoPrestamoLibro, estadoFinalPrestamoLibro: EstadoFinalPrestamoLibro, costoDia: number, fechaPrestamo: string, fechaEstimadaDevolucion: string, fechaDevolucion: string | null, multa: number);
  constructor(idPrestamoLibro: number, idLibro: number, idEstudiante: number, estadoPrestamoLibro: EstadoPrestamoLibro, estadoFinalPrestamoLibro: EstadoFinalPrestamoLibro, costoDia: number, fechaPrestamo: string, fechaEstimadaDevolucion: string, multa: number);
  constructor(idPrestamoLibro: number, idLibro: number, idEstudiante: number, estadoPrestamoLibro: EstadoPrestamoLibro, estadoFinalPrestamoLibro: EstadoFinalPrestamoLibro, costoDia: number, fechaPrestamo: string, fechaEstimadaDevolucion: string, fechaDevolucionOrMulta: string | number | null, multa?: number) {
    this.idPrestamoLibro = idPrestamoLibro;
    this.idLibro = idLibro;
    this.idEstudiante = idEstudiante;
    this.estadoPrestamoLibro = estadoPrestamoLibro;
    this.estadoFinalPrestamoLibro = estadoFinalPrestamoLibro;
    this.costoDia = costoDia;
    this.fechaPrestamo = fechaPrestamo;
    this.fechaEstimadaDevolucion = fechaEstimadaDevolucion;
    if (typeof fechaDevolucionOrMulta === 'number') {
      this.fechaDevolucion = null;
      this.multa = fechaDevolucionOrMulta;
    } else {
      this.fechaDevolucion = fechaDevolucionOrMulta;
      this.multa = multa!;
    }
  }
}
