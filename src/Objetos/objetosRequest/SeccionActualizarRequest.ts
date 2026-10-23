export class SeccionActualizarRequest {
  idGrado!: string;
  anio!: string;
  nombreSeccion!: string;
  idSeccion!: string;

  constructor(idSeccion: string, idGrado: string, anio: string, nombreSeccion: string) {
    this.idSeccion = idSeccion;
    this.idGrado = idGrado;
    this.anio = anio;
    this.nombreSeccion = nombreSeccion;
  }
}
