import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';

@Component({
  selector: 'app-menu-lateral-estudiante',
  imports: [RouterLink],
  templateUrl: './menuLateralEstudiante.html',
})
export class MenuLateralEstudiante {
  constructor(private readonly inicioSesionService: InicioSesionService) {}

  cerrarSesion(): void {
    this.inicioSesionService.cerrarSesion();
  }
}
