export class GradoActualizarRequest {
  nombreGrado!: string;
  gradoColegio!: string;
  idCarrera!: string;
  idGrado!: string;

  constructor(nombreGrado: string, gradoColegio: string, idCarrera: string, idGrado: string) {
    this.nombreGrado = nombreGrado;
    this.gradoColegio = gradoColegio;
    this.idCarrera = idCarrera;
    this.idGrado = idGrado;
  }
}
