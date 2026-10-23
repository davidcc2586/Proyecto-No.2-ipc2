export class AsignacionMaestrosSeccion {
  idSeccion!: number;
  idMaestro!: number;
  idCurso!: number;

  constructor(idSeccion: number, idMaestro: number, idCurso: number) {
    this.idSeccion = idSeccion;
    this.idMaestro = idMaestro;
    this.idCurso = idCurso;
  }
}
