export class UsuarioRequest {
  cui!: string;
  nombre!: string;
  apellido!: string;
  fechaNacimiento!: string;
  usuario!: string;
  clave!: string;
  claveConfirmacion!: string;
  rolUsuario!: string;
  fechaContratacion!: string;
  salario!: string;

  constructor(cui: string, nombre: string, apellido: string, fechaNacimiento: string, usuario: string, clave: string, claveConfirmacion: string, rolUsuario: string, fechaContratacion: string, salario: string) {
    this.cui = cui;
    this.nombre = nombre;
    this.apellido = apellido;
    this.fechaNacimiento = fechaNacimiento;
    this.usuario = usuario;
    this.clave = clave;
    this.claveConfirmacion = claveConfirmacion;
    this.rolUsuario = rolUsuario;
    this.fechaContratacion = fechaContratacion;
    this.salario = salario;
  }
}
