import { TipoActividad } from '../enums/TipoActividad';

export class Actividad {
  idActividad!: number;
  idSeccion!: number;
  idCurso!: number;
  nombreActividad!: string;
  ponderacion!: number;
  tipoActividad!: TipoActividad;

  constructor(idActividad: number, idSeccion: number, idCurso: number, nombreActividad: string, ponderacion: number, tipoActividad: TipoActividad) {
    this.idActividad = idActividad;
    this.idSeccion = idSeccion;
    this.idCurso = idCurso;
    this.nombreActividad = nombreActividad;
    this.ponderacion = ponderacion;
    this.tipoActividad = tipoActividad;
  }
}
