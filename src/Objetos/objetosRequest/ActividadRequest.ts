export class ActividadRequest {
  idSeccion!: string;
  idCurso!: string;
  nombreActividad!: string;
  ponderacion!: string;
  tipoActividad!: string;

  constructor(idSeccion: string, idCurso: string, nombreActividad: string, ponderacion: string, tipoActividad: string) {
    this.idSeccion = idSeccion;
    this.idCurso = idCurso;
    this.nombreActividad = nombreActividad;
    this.ponderacion = ponderacion;
    this.tipoActividad = tipoActividad;
  }
}
