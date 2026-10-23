import { RolUsuario } from "./enums/RolUsuario";

export class InicioSesion{
    idUsuario : number;
    usuario : String;
    rolUsuario : RolUsuario;
    anioLectivo : number;

    constructor(idUsuario : number, usuario : string, rolUsuario : RolUsuario, anioLectivo : number){
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.rolUsuario = rolUsuario;
        this.anioLectivo = anioLectivo;
    }
}