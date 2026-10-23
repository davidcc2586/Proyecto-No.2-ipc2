import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { InicioSesionRequest } from '../../../Objetos/InicioSesionRequest';

@Component({
  selector: 'app-inicio-sesion',
  imports: [FormsModule, RouterLink],
  templateUrl: './inicio-sesion.html',
})
export class InicioSesion {
  mostrarContrasena = false;
  mensaje = '';

  usuario = '';
  clave = '';

  iniciarSesion(formulario: NgForm): void {
    /*if (!formulario.valid) {
      return;
    }
    */
    const inicioSesionRequest = new InicioSesionRequest(this.usuario,this.clave);
    console.log(inicioSesionRequest);
    this.mensaje ='El formulario está listo. Para iniciar sesión, aún falta conectarlo al backend.';
  }
}
