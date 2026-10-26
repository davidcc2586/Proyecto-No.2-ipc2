import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';

@Component({
    selector: 'app-menuLateralBibliotecario',
    templateUrl: './menuLateralBibliotecario.html',
    imports: [RouterLink],
})
export class MenuLateralBibliotecario{
    constructor(private readonly inicioSesionService: InicioSesionService) {}

    cerrarSesion(): void {
        this.inicioSesionService.cerrarSesion();
    }
}
