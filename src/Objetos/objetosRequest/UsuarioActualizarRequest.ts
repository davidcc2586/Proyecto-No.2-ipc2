export class UsuarioActualizarRequest {
  idUsuario!: string;
  clave!: string;
  claveConfirmacion!: string;
  usuario!: string;
  salario!: string;
  rolUsuario!: string;

  constructor(idUsuario: string, clave: string, claveConfirmacion: string, usuario: string, salario: string, rolUsuario: string) {
    this.idUsuario = idUsuario;
    this.clave = clave;
    this.claveConfirmacion = claveConfirmacion;
    this.usuario = usuario;
    this.salario = salario;
    this.rolUsuario = rolUsuario;
  }
}
