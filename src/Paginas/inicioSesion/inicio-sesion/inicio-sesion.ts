import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';
import { InicioSesionRequest } from '../../../Objetos/InicioSesionRequest';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';

@Component({
  selector: 'app-inicio-sesion',
  imports: [FormsModule, RouterLink],
  templateUrl: './inicio-sesion.html',
})
export class InicioSesion {
  mostrarContrasena = false;
  mensaje = '';
  error = false;
  cargando = false;

  usuario = '';
  clave = '';

  constructor(
    private readonly inicioSesionService: InicioSesionService,
    private readonly router: Router,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  iniciarSesion(formulario: NgForm): void {
    if (formulario.invalid || this.cargando) {
      return;
    }

    const inicioSesionRequest = new InicioSesionRequest(this.usuario, this.clave);
    this.cargando = true;
    this.mensaje = '';
    this.error = false;

    this.inicioSesionService
      .iniciarSesion(inicioSesionRequest.usuario, inicioSesionRequest.clave)
      .subscribe({
        next: (sesion) => {
          const rutaInicio = this.inicioSesionService.rutaInicio(sesion.rolUsuario);
          if (!rutaInicio) {
            this.inicioSesionService.cerrarSesion();
            this.mostrarError('Tu rol todavía no tiene un panel habilitado.');
            return;
          }

          this.inicioSesionService.setSesion(sesion);
          this.cargando = false;
          this.changeDetector.markForCheck();
          void this.router.navigateByUrl(rutaInicio);
        },
        error: (respuesta: HttpErrorResponse) => {
          const detalle = respuesta.error;
          const mensajeApi =
            typeof detalle === 'object' && detalle !== null && 'error' in detalle
              ? detalle.error
              : null;
          this.mostrarError(
            typeof mensajeApi === 'string'
              ? mensajeApi
              : 'No se pudo iniciar sesión. Verifica tus credenciales e inténtalo de nuevo.',
          );
        },
      });
  }

  private mostrarError(mensaje: string): void {
    this.cargando = false;
    this.error = true;
    this.mensaje = mensaje;
    this.changeDetector.markForCheck();
  }
}
