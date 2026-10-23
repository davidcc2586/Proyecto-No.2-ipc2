import { RolUsuario } from '../enums/RolUsuario';

export class Usuario {
  idUsuario!: number;
  cui!: string;
  nombre!: string;
  apellido!: string;
  fechaNacimiento!: string;
  usuario!: string;
  clave!: string;
  rolUsuario!: RolUsuario;
  fechaContratacion!: string;
  salario!: number;

  constructor(idUsuario: number, cui: string, nombre: string, apellido: string, fechaNacimiento: string, usuario: string, clave: string, rolUsuario: RolUsuario, fechaContratacion: string, salario: number) {
    this.idUsuario = idUsuario;
    this.cui = cui;
    this.nombre = nombre;
    this.apellido = apellido;
    this.fechaNacimiento = fechaNacimiento;
    this.usuario = usuario;
    this.clave = clave;
    this.rolUsuario = rolUsuario;
    this.fechaContratacion = fechaContratacion;
    this.salario = salario;
  }
}
