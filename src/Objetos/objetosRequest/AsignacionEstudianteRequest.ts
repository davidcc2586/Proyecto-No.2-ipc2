export class AsignacionEstudianteRequest {
  idSeccion!: string;
  isEstudiante!: string;

  constructor(idSeccion: string, isEstudiante: string) {
    this.idSeccion = idSeccion;
    this.isEstudiante = isEstudiante;
  }
}
