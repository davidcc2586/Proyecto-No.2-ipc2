import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';

@Component({
  selector: 'app-menu-lateral-secretaria',
  imports: [RouterLink],
  templateUrl: './menuLateralSecretaria.html',
})
export class MenuLateralSecretaria {
  constructor(private readonly inicioSesionService: InicioSesionService) {}

  cerrarSesion(): void {
    this.inicioSesionService.cerrarSesion();
  }
}
