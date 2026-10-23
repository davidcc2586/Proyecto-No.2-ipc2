export class Solvencia {
  encabezado!: string;
  titulo!: string;
  firmas!: string;
  nombreEstudiante!: string;
  cuiEstudiante!: string;
  gradoSeccion!: string;
  anioLectivo!: number;
  estadoSolvencia!: string;
  mes!: number;

  constructor(nombreEstudiante: string, cuiEstudiante: string, gradoSeccion: string, anioLectivo: number, estadoSolvencia: string, mes: number) {
    this.nombreEstudiante = nombreEstudiante;
    this.cuiEstudiante = cuiEstudiante;
    this.gradoSeccion = gradoSeccion;
    this.anioLectivo = anioLectivo;
    this.estadoSolvencia = estadoSolvencia;
    this.mes = mes;
  }
}
