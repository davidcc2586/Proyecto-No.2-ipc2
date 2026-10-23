import { EstadoFinalPrestamoLibro } from '../enums/EstadoFinalPrestamoLibro';
import { EstadoPrestamoLibro } from '../enums/EstadoPrestamoLibro';

export class PrestamoLibroResponse {
  idPrestamoLibro!: number;
  idLibro!: number;
  idEstudiante!: number;
  estadoPrestamoLibro!: EstadoPrestamoLibro;
  estadoFinalPrestamoLibro!: EstadoFinalPrestamoLibro;
  costoDia!: number;
  fechaPrestamo!: string;
  fechaEstimadaDevolucion!: string;
  fechaDevolucion!: string;
  multa!: number;
  nombreLibro!: string;

  constructor(idPrestamoLibro: number, idLibro: number, nombreLibro: string, idEstudiante: number, estadoPrestamoLibro: EstadoPrestamoLibro, estadoFinalPrestamoLibro: EstadoFinalPrestamoLibro, costoDia: number, fechaPrestamo: string, fechaEstimadaDevolucion: string, fechaDevolucion: string, multa: number) {
    this.idPrestamoLibro = idPrestamoLibro;
    this.idLibro = idLibro;
    this.nombreLibro = nombreLibro;
    this.idEstudiante = idEstudiante;
    this.estadoPrestamoLibro = estadoPrestamoLibro;
    this.estadoFinalPrestamoLibro = estadoFinalPrestamoLibro;
    this.costoDia = costoDia;
    this.fechaPrestamo = fechaPrestamo;
    this.fechaEstimadaDevolucion = fechaEstimadaDevolucion;
    this.multa = multa;
  }
}
