export class GradoRequest {
  nombreGrado!: string;
  gradoColegio!: string;
  idCarrera!: string;

  constructor(nombreGrado: string, gradoColegio: string, idCarrera: string) {
    this.nombreGrado = nombreGrado;
    this.gradoColegio = gradoColegio;
    this.idCarrera = idCarrera;
  }
}
