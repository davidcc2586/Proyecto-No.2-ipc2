import { Routes } from '@angular/router';
import { Autenticacion } from '../Paginas/inicioSesion/autenticacion/autenticacion';
import { RecuperarContrasena } from '../Paginas/inicioSesion/recuperar-contrasena/recuperar-contrasena';
import { InicioSesion } from '../Paginas/inicioSesion/inicio-sesion/inicio-sesion';
import { PanelSuperAdmin } from '../Paginas/superAdminComponentes/panelSuperAdmin/panelSuperAdmin';
import { PanelCentralSuperAdmin } from '../Paginas/superAdminComponentes/panelCentralSuperAdmin/panelCentralSuperAdmin';
import { PanelControlUsuariosAdmin } from '../Paginas/superAdminComponentes/panelControlUsuariosAdmin/panelControlUsuariosAdmin';

export const routes: Routes = [
  { 
    path: '', 
    component: Autenticacion,
    children: [
      { path: 'iniciar-sesion', component: InicioSesion },
      { path: 'recuperar-contrasena', component: RecuperarContrasena },
      
      { path: '', redirectTo: 'iniciar-sesion', pathMatch: 'full' }
    ]
  },
  { path: 'inicioSuperAdmin', 
    component: PanelSuperAdmin,
    children: [
      {path:'panelInicio', component: PanelCentralSuperAdmin},
      {path:'panelControlUsuariosAdmin', component: PanelControlUsuariosAdmin},
      {path: '', redirectTo: 'panelInicio', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: 'iniciar-sesion' },
  
];
