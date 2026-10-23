export class SelectGradosResponse {
  idGrado!: number;
  nombreGrado!: string;

  constructor(idGrado: number, nombreGrado: string) {
    this.idGrado = idGrado;
    this.nombreGrado = nombreGrado;
  }
}
