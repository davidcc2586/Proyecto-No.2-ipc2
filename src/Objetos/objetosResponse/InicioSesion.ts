import { RolUsuario } from '../enums/RolUsuario';

export class InicioSesion {
  idUsuario!: number;
  usuario!: string;
  rolUsuario!: RolUsuario;
  anioLectivo!: number;
  accessToken!: string;
  expiresAt!: number;

  constructor(
    idUsuario: number,
    usuario: string,
    rolUsuario: RolUsuario,
    anioLectivo: number,
    accessToken: string,
    expiresAt: number,
  ) {
    this.idUsuario = idUsuario;
    this.usuario = usuario;
    this.rolUsuario = rolUsuario;
    this.anioLectivo = anioLectivo;
    this.accessToken = accessToken;
    this.expiresAt = expiresAt;
  }
}
