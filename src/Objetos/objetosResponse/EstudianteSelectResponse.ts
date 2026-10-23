export class EstudianteSelectResponse {
  idEstudiante!: number;
  nombreCompleto!: string;

  constructor(idEstudiante: number, nombreCompleto: string) {
    this.idEstudiante = idEstudiante;
    this.nombreCompleto = nombreCompleto;
  }
}
