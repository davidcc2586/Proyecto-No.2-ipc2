export class Factura {
  idFactura!: number;
  idPago!: number;
  nombreConsumidor!: string;
  nit!: string;

  constructor(idFactura: number, idPago: number, nombreConsumidor: string, nit: string) {
    this.idFactura = idFactura;
    this.idPago = idPago;
    this.nombreConsumidor = nombreConsumidor;
    this.nit = nit;
  }
}
