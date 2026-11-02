export class EstudianteActualizarRequest {
  idEstudiante!: number;
  alergia!: string;
  nombreEncargado!: string;
  telefonoEncargado!: string;
  correoEncargado!: string;

  constructor(
    idEstudiante: number,
    alergia: string,
    nombreEncargado: string,
    telefonoEncargado: string,
    correoEncargado: string,
  ) {
    this.idEstudiante = idEstudiante;
    this.alergia = alergia;
    this.nombreEncargado = nombreEncargado;
    this.telefonoEncargado = telefonoEncargado;
    this.correoEncargado = correoEncargado;
  }
}
