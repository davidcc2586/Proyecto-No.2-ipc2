export class EstudianteRequest {
  cui!: string;
  nombre!: string;
  apellido!: string;
  fechaNacimiento!: string;
  usuario!: string;
  clave!: string;
  claveConfirmacion!: string;
  anioInscripcion!: string;
  tipoSangre!: string;
  alergia!: string;
  nombreEncargado!: string;
  telefonoEncargado!: string;
  correoEncargado!: string;

  constructor(cui: string, nombre: string, apellido: string, fechaNacimiento: string, usuario: string, clave: string, claveConfirmacion: string, anioInscripcion: string, tipoSangre: string, alergia: string, nombreEncargado: string, telefonoEncargado: string, correoEncargado: string) {
    this.cui = cui;
    this.nombre = nombre;
    this.apellido = apellido;
    this.fechaNacimiento = fechaNacimiento;
    this.usuario = usuario;
    this.clave = clave;
    this.claveConfirmacion = claveConfirmacion;
    this.anioInscripcion = anioInscripcion;
    this.tipoSangre = tipoSangre;
    this.alergia = alergia;
    this.nombreEncargado = nombreEncargado;
    this.telefonoEncargado = telefonoEncargado;
    this.correoEncargado = correoEncargado;
  }
}
