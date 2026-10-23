export class SeccionRequest {
  idGrado!: string;
  anio!: string;
  nombreSeccion!: string;

  constructor(idGrado: string, anio: string, nombreSeccion: string) {
    this.idGrado = idGrado;
    this.anio = anio;
    this.nombreSeccion = nombreSeccion;
  }
}
