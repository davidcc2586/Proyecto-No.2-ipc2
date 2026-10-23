export class AsignarMaestroRequest {
  idSeccion!: string;
  idMaestro!: string;
  idCurso!: string;

  constructor(idSeccion: string, idMaestro: string, idCurso: string) {
    this.idSeccion = idSeccion;
    this.idMaestro = idMaestro;
    this.idCurso = idCurso;
  }
}
