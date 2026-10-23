export class CurriculumCursosGradoRequest {
  idGrado!: string;
  idCurso!: string;

  constructor(idGrado: string, idCurso: string) {
    this.idGrado = idGrado;
    this.idCurso = idCurso;
  }
}
