export class Curso {
  idCurso!: number;
  nombreCurso!: string;

  constructor(idCurso: number, nombreCurso: string) {
    this.idCurso = idCurso;
    this.nombreCurso = nombreCurso;
  }
}
