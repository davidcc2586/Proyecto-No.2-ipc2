export class NotaRequest {
  idActividad!: string;
  idEstudiante!: string;
  punteoObtenido!: string;

  constructor(idActividad: string, idEstudiante: string, punteoObtenido: string) {
    this.idActividad = idActividad;
    this.idEstudiante = idEstudiante;
    this.punteoObtenido = punteoObtenido;
  }
}
