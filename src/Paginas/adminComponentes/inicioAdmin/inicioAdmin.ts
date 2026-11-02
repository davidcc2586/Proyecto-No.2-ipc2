import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Autenticacion } from '../../accesorios/cabecera/cabecera';
import { MenuLateralAdmin } from '../menuLateralAdmin/menuLateralAdmin';

@Component({
  selector: 'app-inicio-admin',
  imports: [Autenticacion, MenuLateralAdmin, RouterOutlet],
  templateUrl: './inicioAdmin.html',
})
export class InicioAdmin {}
