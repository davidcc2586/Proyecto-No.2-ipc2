import { EstadoEstudiante } from '../enums/EstadoEstudiante';

export class Estudiante {
  idEstudiante!: number;
  cui!: string;
  nombre!: string;
  apellido!: string;
  fechaNacimiento!: string;
  usuario!: string;
  clave!: string;
  anioInscripcion!: number;
  estadoEstudiante!: EstadoEstudiante;
  tipoSangre!: string;
  alergia!: string;
  nombreEncargado!: string;
  telefonoEncargado!: string;
  correoEncargado!: string;

  constructor(idEstudiante: number, cui: string, nombre: string, apellido: string, fechaNacimiento: string, usuario: string, clave: string, anioInscripcion: number, estadoEstudiante: EstadoEstudiante, tipoSangre: string, alergia: string, nombreEncargado: string, telefonoEncargado: string, correoEncargado: string) {
    this.idEstudiante = idEstudiante;
    this.cui = cui;
    this.nombre = nombre;
    this.apellido = apellido;
    this.fechaNacimiento = fechaNacimiento;
    this.usuario = usuario;
    this.clave = clave;
    this.anioInscripcion = anioInscripcion;
    this.estadoEstudiante = estadoEstudiante;
    this.tipoSangre = tipoSangre;
    this.alergia = alergia;
    this.nombreEncargado = nombreEncargado;
    this.telefonoEncargado = telefonoEncargado;
    this.correoEncargado = correoEncargado;
  }
}
