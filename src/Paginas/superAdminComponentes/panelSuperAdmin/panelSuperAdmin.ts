import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Autenticacion } from '../../accesorios/cabecera/cabecera';
import { MenuLateralSuperAdmin } from '../menuLateralSuperAdmin/menuLateral';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-panelSuperAdmin',
  imports: [FormsModule, Autenticacion, MenuLateralSuperAdmin, RouterOutlet],
  templateUrl: './panelSuperAdmin.html',
})
export class PanelSuperAdmin{
}
