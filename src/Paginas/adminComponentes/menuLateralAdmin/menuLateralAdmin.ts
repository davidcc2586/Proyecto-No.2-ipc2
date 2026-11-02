import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';

@Component({
  selector: 'app-menu-lateral-admin',
  imports: [RouterLink],
  templateUrl: './menuLateralAdmin.html',
})
export class MenuLateralAdmin {
  constructor(private readonly inicioSesionService: InicioSesionService) {}

  cerrarSesion(): void {
    this.inicioSesionService.cerrarSesion();
  }
}
