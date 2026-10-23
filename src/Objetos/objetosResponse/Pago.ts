import { TipoPago } from '../enums/TipoPago';

export class Pago {
  idPago!: number;
  idEstudiante!: number;
  idSecretario!: number;
  tipoPago!: TipoPago;
  cantidad!: number;
  descripcion!: string;
  fecha!: string;

  constructor(idPago: number, idEstudiante: number, idSecretario: number, tipoPago: TipoPago, cantidad: number, descripcion: string, fecha: string) {
    this.idPago = idPago;
    this.idEstudiante = idEstudiante;
    this.idSecretario = idSecretario;
    this.tipoPago = tipoPago;
    this.cantidad = cantidad;
    this.descripcion = descripcion;
    this.fecha = fecha;
  }
}
