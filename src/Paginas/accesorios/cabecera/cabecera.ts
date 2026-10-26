import { Component, input } from '@angular/core';

@Component({
  selector: 'app-cabecera',
  templateUrl: './cabecera.html',
})
export class Autenticacion {
    rol = input.required<String>();
}
