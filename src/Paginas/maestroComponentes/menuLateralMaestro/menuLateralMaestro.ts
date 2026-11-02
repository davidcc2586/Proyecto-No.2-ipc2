import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';

@Component({
  selector: 'app-menu-lateral-maestro',
  imports: [RouterLink],
  templateUrl: './menuLateralMaestro.html',
})
export class MenuLateralMaestro {
  constructor(private readonly inicioSesionService: InicioSesionService) {}

  cerrarSesion(): void {
    this.inicioSesionService.cerrarSesion();
  }
}
