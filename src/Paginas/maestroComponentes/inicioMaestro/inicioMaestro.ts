import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Autenticacion } from '../../accesorios/cabecera/cabecera';
import { MenuLateralMaestro } from '../menuLateralMaestro/menuLateralMaestro';

@Component({
  selector: 'app-inicio-maestro',
  imports: [Autenticacion, MenuLateralMaestro, RouterOutlet],
  templateUrl: './inicioMaestro.html',
})
export class InicioMaestro {}
