import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';

@Component({
    selector: 'app-menuLateralSuperAdmin',
    templateUrl: './menuLateralSuperAdmin.html',
    imports: [RouterLink],
})
export class MenuLateralSuperAdmin{
    constructor(private readonly inicioSesionService: InicioSesionService) {}

    cerrarSesion(): void {
        this.inicioSesionService.cerrarSesion();
    }
}
