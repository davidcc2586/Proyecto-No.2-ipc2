export class NotaActividad {
  idActividad!: number;
  idEstudiante!: number;
  punteoObtenido!: number;

  constructor(idActividad: number, idEstudiante: number, punteoObtenido: number) {
    this.idActividad = idActividad;
    this.idEstudiante = idEstudiante;
    this.punteoObtenido = punteoObtenido;
  }
}
