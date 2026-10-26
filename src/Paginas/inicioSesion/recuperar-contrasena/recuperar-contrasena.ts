import { ChangeDetectorRef, Component } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule, NgForm } from '@angular/forms';
import { RecuperarClaveRequest } from '../../../Objetos/objetosRequest/RecuperarClaveRequest';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';

@Component({
  selector: 'app-recuperar-contrasena',
  imports: [FormsModule],
  templateUrl: './recuperar-contrasena.html',
})
export class RecuperarContrasena {
  estado = '';
  error = false;
  cargando = false;
  claveRecuperada = '';
  usuario = '';
  fechaNacimiento = '';
  cui = '';

  constructor(
    private readonly inicioSesionService: InicioSesionService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  recuperarContrasena(formulario: NgForm): void {
    if (formulario.invalid || this.cargando) {
      return;
    }

    const datosRecuperacion = new RecuperarClaveRequest(
      this.usuario,
      this.fechaNacimiento,
      this.cui,
    );
    this.estado = '';
    this.error = false;
    this.claveRecuperada = '';
    this.cargando = true;

    this.inicioSesionService.recuperarClave(datosRecuperacion).subscribe({
      next: (respuesta) => {
        this.cargando = false;
        if (!respuesta.clave) {
          this.error = true;
          this.estado = 'El servidor no devolvió una clave para esta cuenta.';
          this.changeDetector.markForCheck();
          return;
        }

        this.claveRecuperada = respuesta.clave;
        this.estado = 'Se recuperó la clave de la cuenta.';
        this.changeDetector.markForCheck();
      },
      error: (respuesta: HttpErrorResponse) => {
        const detalle = respuesta.error;
        const mensajeApi =
          typeof detalle === 'object' && detalle !== null && 'error' in detalle
            ? detalle.error
            : null;
        this.cargando = false;
        this.error = true;
        this.estado =
          typeof mensajeApi === 'string'
            ? mensajeApi
            : 'No se pudo recuperar la clave. Verifica los datos ingresados e inténtalo de nuevo.';
        this.changeDetector.markForCheck();
      },
    });
  }
}