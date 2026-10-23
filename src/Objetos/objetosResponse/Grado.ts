import { GradoColegio } from '../enums/GradoColegio';

export class Grado {
  idGrado!: number;
  nombreGrado!: string | null;
  gradoColegio!: GradoColegio;
  idCarrera!: number;

  constructor(idGrado: number, gradoColegio: GradoColegio);
  constructor(idGrado: number, nombreGrado: string, gradoColegio: GradoColegio, idCarrera: number);
  constructor(idGrado: number, nombreGradoOrGradoColegio: string | GradoColegio, gradoColegio?: GradoColegio, idCarrera?: number) {
    this.idGrado = idGrado;
    if (idCarrera !== undefined) {
      this.nombreGrado = String(nombreGradoOrGradoColegio);
      this.gradoColegio = gradoColegio!;
      this.idCarrera = idCarrera;
    } else {
      this.nombreGrado = null;
      this.gradoColegio = nombreGradoOrGradoColegio as GradoColegio;
      this.idCarrera = 0;
    }
  }
}
