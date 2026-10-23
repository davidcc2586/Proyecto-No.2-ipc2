export class PagoRequest {
  idEstudiante!: string;
  idSecretario!: string;
  tipoPago!: string;
  cantidad!: string;
  descripcion!: string;
  fecha!: string;

  constructor(idEstudiante: string, idSecretario: string, tipoPago: string, cantidad: string, descripcion: string, fecha: string) {
    this.idEstudiante = idEstudiante;
    this.idSecretario = idSecretario;
    this.tipoPago = tipoPago;
    this.cantidad = cantidad;
    this.descripcion = descripcion;
    this.fecha = fecha;
  }
}
