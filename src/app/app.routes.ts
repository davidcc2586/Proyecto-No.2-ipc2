import { Routes } from '@angular/router';
import { Autenticacion } from '../Paginas/inicioSesion/autenticacion/autenticacion';
import { RecuperarContrasena } from '../Paginas/inicioSesion/recuperar-contrasena/recuperar-contrasena';
import { InicioSesion } from '../Paginas/inicioSesion/inicio-sesion/inicio-sesion';
import { PanelSuperAdmin } from '../Paginas/superAdminComponentes/panelSuperAdmin/panelSuperAdmin';
import { PanelCentralSuperAdmin } from '../Paginas/superAdminComponentes/panelCentralSuperAdmin/panelCentralSuperAdmin';
import { PanelControlUsuariosAdmin } from '../Paginas/superAdminComponentes/panelControlUsuariosAdmin/panelControlUsuariosAdmin';
import { PanelConfiguracionSistema } from '../Paginas/superAdminComponentes/panelConfiguracionSistema/panelConfiguracionSistema';
import { PanelBiblioteca } from '../Paginas/bibliotecaComponentes/panelBiblioteca/panelBiblioteca';
import { CatalogoLibros } from '../Paginas/bibliotecaComponentes/catalogoLibros/catalogoLibros';
import { PrestamosBiblioteca } from '../Paginas/bibliotecaComponentes/prestamosBiblioteca/prestamosBiblioteca';
import { DevolucionesBiblioteca } from '../Paginas/bibliotecaComponentes/devolucionesBiblioteca/devolucionesBiblioteca';
import { InicioSecretaria } from '../Paginas/secretariaComponentes/inicioSecretaria/inicioSecretaria';
import { CobrosSecretaria } from '../Paginas/secretariaComponentes/cobrosSecretaria/cobrosSecretaria';
import { SolvenciaSecretaria } from '../Paginas/secretariaComponentes/solvenciaSecretaria/solvenciaSecretaria';
import { rolGuard } from './guards/rol.guard';

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
    canActivate: [rolGuard],
    data: { rutaPermitida: '/inicioSuperAdmin' },
    children: [
      {path:'panelInicio', component: PanelCentralSuperAdmin},
      {path:'panelControlUsuariosAdmin', component: PanelControlUsuariosAdmin},
      {path:'panelConfiguracionSistema', component: PanelConfiguracionSistema},
      {path: '', redirectTo: 'panelInicio', pathMatch: 'full' }
    ]
  },
  {
    path: "inicioBibliotecario",
    component: PanelBiblioteca,
    canActivate: [rolGuard],
    data: { rutaPermitida: '/inicioBibliotecario' },
    children: [
      { path: 'catalogo', component: CatalogoLibros },
      { path: 'prestamos', component: PrestamosBiblioteca },
      { path: 'devoluciones', component: DevolucionesBiblioteca },
      { path: '', redirectTo: 'catalogo', pathMatch: 'full' }
    ]
  },
  {
    path: 'inicioSecretaria',
    component: InicioSecretaria,
    canActivate: [rolGuard],
    data: { rutaPermitida: '/inicioSecretaria' },
    children: [
      { path: 'cobros', component: CobrosSecretaria },
      { path: 'solvencia', component: SolvenciaSecretaria },
      { path: '', redirectTo: 'cobros', pathMatch: 'full' },
    ],
  },
  { path: '**', redirectTo: 'iniciar-sesion' },
  
];
