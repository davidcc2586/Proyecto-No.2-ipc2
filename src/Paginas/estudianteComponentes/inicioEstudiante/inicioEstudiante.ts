import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Autenticacion } from '../../accesorios/cabecera/cabecera';
import { MenuLateralEstudiante } from '../menuLateralEstudiante/menuLateralEstudiante';

@Component({
  selector: 'app-inicio-estudiante',
  imports: [Autenticacion, MenuLateralEstudiante, RouterOutlet],
  templateUrl: './inicioEstudiante.html',
})
export class InicioEstudiante {}
