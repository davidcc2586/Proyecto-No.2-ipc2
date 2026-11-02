export class PagoRequest {
  idEstudiante!: string;
  idSecretario!: string;
  tipoPago!: string;
  cantidad!: string;
  descripcion!: string;
  fecha!: string;
  nombreConsumidor!: string;
  nit!: string;

  constructor(
    idEstudiante: string,
    idSecretario: string,
    tipoPago: string,
    cantidad: string,
    descripcion: string,
    fecha: string,
    nombreConsumidor = 'CONSUMIDOR FINAL',
    nit = 'CF',
  ) {
    this.idEstudiante = idEstudiante;
    this.idSecretario = idSecretario;
    this.tipoPago = tipoPago;
    this.cantidad = cantidad;
    this.descripcion = descripcion;
    this.fecha = fecha;
    this.nombreConsumidor = nombreConsumidor;
    this.nit = nit;
  }
}
