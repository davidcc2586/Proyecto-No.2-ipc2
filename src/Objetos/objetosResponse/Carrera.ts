import { EstadoCarrera } from '../enums/EstadoCarrera';

export class Carrera {
  idCarrera!: number;
  nombreCarrera!: string;
  estadoCarrera!: EstadoCarrera;

  constructor(idCarrera: number, nombreCarrera: string, estadoCarrera: EstadoCarrera) {
    this.idCarrera = idCarrera;
    this.nombreCarrera = nombreCarrera;
    this.estadoCarrera = estadoCarrera;
  }
}
