export class CarreraActualizarRequest {
  idCarrera!: string;
  nombreCarrera!: string;

  constructor(idCarrera: string, nombreCarrera: string) {
    this.idCarrera = idCarrera;
    this.nombreCarrera = nombreCarrera;
  }
}
