import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { RecuperarClaveRequest } from '../../../Objetos/RecuperarClaveRequest';

@Component({
  selector: 'app-recuperar-contrasena',
  imports: [FormsModule],
  templateUrl: './recuperar-contrasena.html',
})
export class RecuperarContrasena {
  estado = '';
  usuario = '';
  fechaNacimiento = '';
  cui = '';

  recuperarContrasena(formulario: NgForm): void {
    /*
    if (!formulario.valid) {
      return;
    }
    */

    // Aquí ya puedes crear tu objeto usando los datos de las propiedades de la clase
    const datosRecuperacion = new RecuperarClaveRequest(this.usuario, this.fechaNacimiento, this.cui);

    console.log(datosRecuperacion);
    this.estado = 'El formulario está listo. Para enviar instrucciones, aún falta conectarlo al backend.';
  }
}