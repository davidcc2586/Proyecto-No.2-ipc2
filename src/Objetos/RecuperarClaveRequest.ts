export class RecuperarClaveRequest{
    usuario : string;
    fechaNacimiento : string;
    cui : string;

    constructor(usuario : string, fechaNacimiento : string, cui : string){
        this.usuario = usuario;
        this.fechaNacimiento = fechaNacimiento;
        this.cui = cui;
    }
}