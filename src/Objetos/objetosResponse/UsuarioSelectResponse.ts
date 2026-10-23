export class UsuarioSelectResponse {
  idUsuario!: number;
  nombreCompleto!: string;

  constructor(idUsuario: number, nombreCompleto: string) {
    this.idUsuario = idUsuario;
    this.nombreCompleto = nombreCompleto;
  }
}
