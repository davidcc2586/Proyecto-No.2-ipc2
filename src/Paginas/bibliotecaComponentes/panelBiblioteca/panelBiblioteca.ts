import { Component } from '@angular/core';
import { Autenticacion } from '../../accesorios/cabecera/cabecera';
import { RouterOutlet } from '@angular/router';
import { MenuLateralBibliotecario } from '../menuLateralBibliotecario/menuLateralBibliotecario';

@Component({
    selector: 'app-panel-biblioteca',
    templateUrl: './panelBiblioteca.html',
    imports: [Autenticacion, RouterOutlet, MenuLateralBibliotecario],
})
export class PanelBiblioteca{
}
