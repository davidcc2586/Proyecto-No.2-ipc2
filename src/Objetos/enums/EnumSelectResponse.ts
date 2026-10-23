export class EnumSelectResponse {
  valor!: string;
  etiqueta!: string;

  constructor(valor: string, etiqueta: string) {
    this.valor = valor;
    this.etiqueta = etiqueta;
  }
}
