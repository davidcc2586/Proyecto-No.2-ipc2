import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
    selector: 'app-navBarSuperAdmin',
    imports: [FormsModule, RouterLink, RouterLinkActive],
    templateUrl: './navBarSuperAdmin.html',
})
export class InicioSesion {
}
