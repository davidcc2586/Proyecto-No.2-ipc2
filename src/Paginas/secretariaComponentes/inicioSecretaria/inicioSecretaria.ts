import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CabeceraSecretaria } from '../cabeceraSecretaria/cabeceraSecretaria';
import { MenuLateralSecretaria } from '../menuLateralSecretaria/menuLateralSecretaria';

@Component({
  selector: 'app-inicio-secretaria',
  imports: [CabeceraSecretaria, MenuLateralSecretaria, RouterOutlet],
  templateUrl: './inicioSecretaria.html',
})
export class InicioSecretaria {}
