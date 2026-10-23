export class InicioSesionRequest {
  usuario!: string;
  clave!: string;

  constructor(usuario: string, contraseña: string) {
    this.usuario = usuario;
    this.clave = contraseña;
  }
}
