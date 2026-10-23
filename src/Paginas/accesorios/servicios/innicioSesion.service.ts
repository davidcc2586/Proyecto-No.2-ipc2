import { Injectable } from "@angular/core";
import { InicioSesion } from "../navBarSuperAdmin/navBarSuperAdmin";


@Injectable({providedIn : 'root'})
export class InicioSesionSevice{
  private sesion : InicioSesion  = "";
  
  setSesion(sesion : InicioSesion){
    this.sesion = sesion;
  }
  getSesion(){
    return this.sesion
  }
}

/*
  private int idUsuario;
    private String usuario;
    private RolUsuario rolUsuario;
    private int anioLectivo;
    */

