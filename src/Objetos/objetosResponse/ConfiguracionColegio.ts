import { Estado } from '../enums/Estado';

export class ConfiguracionColegio {
  anioLectivo!: number;
  estado!: Estado;

  constructor(anioLectivo: number, estado: Estado) {
    this.anioLectivo = anioLectivo;
    this.estado = estado;
  }
}
