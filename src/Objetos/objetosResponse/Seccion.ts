export class Seccion {
  idSeccion!: number;
  idGrado!: number;
  anio!: number;
  seccion!: string;

  constructor(idSeccion: number, idGrado: number, anio: number, seccion: string) {
    this.idSeccion = idSeccion;
    this.idGrado = idGrado;
    this.anio = anio;
    this.seccion = seccion;
  }
}
