import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-cabecera',
  templateUrl: './cabecera.html',
  imports: [RouterLink],
})
export class Autenticacion {
    rol = input.required<String>();
}
