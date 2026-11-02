import { EstadoEstudiante } from '../enums/EstadoEstudiante';

export class EstudianteDatosResponse {
  cui!: string;
  nombre!: string;
  apellido!: string;
  fechaNacimiento!: string;
  tipoSangre!: string;
  alergia!: string;
  nombreEncargado!: string;
  telefonoEncargado!: string;
  correoEncargado!: string;
  anioInscripcion!: number;
  estadoEstudiante!: EstadoEstudiante;
}
