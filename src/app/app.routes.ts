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
import { InscripcionSecretaria } from '../Paginas/secretariaComponentes/inscripcionSecretaria/inscripcionSecretaria';
import { SolvenciaSecretaria } from '../Paginas/secretariaComponentes/solvenciaSecretaria/solvenciaSecretaria';
import { FacturasSecretaria } from '../Paginas/secretariaComponentes/facturasSecretaria/facturasSecretaria';
import { rolGuard } from './guards/rol.guard';
import { InicioAdmin } from '../Paginas/adminComponentes/inicioAdmin/inicioAdmin';
import { GestionCursosAdmin } from '../Paginas/adminComponentes/gestionCursosAdmin/gestionCursosAdmin';
import { GestionCarrerasAdmin } from '../Paginas/adminComponentes/gestionCarrerasAdmin/gestionCarrerasAdmin';
import { EstructuraAcademicaAdmin } from '../Paginas/adminComponentes/estructuraAcademicaAdmin/estructuraAcademicaAdmin';
import { CurriculoAdmin } from '../Paginas/adminComponentes/curriculoAdmin/curriculoAdmin';
import { ConsultaEstudiantes } from '../Paginas/accesorios/consultaEstudiantes/consultaEstudiantes';
import { InicioEstudiante } from '../Paginas/estudianteComponentes/inicioEstudiante/inicioEstudiante';
import { PerfilEstudiante } from '../Paginas/estudianteComponentes/perfilEstudiante/perfilEstudiante';
import { CursosEstudiante } from '../Paginas/estudianteComponentes/cursosEstudiante/cursosEstudiante';
import { SolvenciaEstudiante } from '../Paginas/estudianteComponentes/solvenciaEstudiante/solvenciaEstudiante';
import { BibliotecaEstudiante } from '../Paginas/estudianteComponentes/bibliotecaEstudiante/bibliotecaEstudiante';
import { InicioMaestro } from '../Paginas/maestroComponentes/inicioMaestro/inicioMaestro';
import { CursosMaestro } from '../Paginas/maestroComponentes/cursosMaestro/cursosMaestro';
import { NotasMaestro } from '../Paginas/maestroComponentes/notasMaestro/notasMaestro';
import { ControlAsistenciaMaestro } from '../Paginas/maestroComponentes/controlAsistenciaMaestro/controlAsistenciaMaestro';
import { NotasEstudiante } from '../Paginas/estudianteComponentes/notasEstudiante/notasEstudiante';
import { EstadoCuentaEstudiante } from '../Paginas/estudianteComponentes/estadoCuentaEstudiante/estadoCuentaEstudiante';
import { Reportes } from '../Paginas/accesorios/reportes/reportes';
import { RolUsuario } from '../Objetos/enums/RolUsuario';

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
    canActivateChild: [rolGuard],
    data: { allowedRoles: [RolUsuario.SUPERADMIN] },
    children: [
      {path:'panelInicio', component: PanelCentralSuperAdmin},
      {path:'panelControlUsuariosAdmin', component: PanelControlUsuariosAdmin},
      {path:'panelConfiguracionSistema', component: PanelConfiguracionSistema},
      {path:'reportes', component: Reportes},
      {path: '', redirectTo: 'panelInicio', pathMatch: 'full' }
    ]
  },
  {
    path: 'inicioAdmin',
    component: InicioAdmin,
    canActivate: [rolGuard],
    canActivateChild: [rolGuard],
    data: { allowedRoles: [RolUsuario.ADMIN] },
    children: [
      { path: 'usuarios', component: PanelControlUsuariosAdmin },
      { path: 'cursos', component: GestionCursosAdmin },
      { path: 'estructura', component: EstructuraAcademicaAdmin },
      { path: 'curriculo', component: CurriculoAdmin },
      { path: 'carreras', component: GestionCarrerasAdmin },
      { path: 'estudiantes', component: ConsultaEstudiantes },
      { path: 'reportes', component: Reportes },
      { path: '', redirectTo: 'usuarios', pathMatch: 'full' },
    ],
  },
  {
    path: "inicioBibliotecario",
    component: PanelBiblioteca,
    canActivate: [rolGuard],
    canActivateChild: [rolGuard],
    data: { allowedRoles: [RolUsuario.BIBLIOTECARIO] },
    children: [
      { path: 'catalogo', component: CatalogoLibros },
      { path: 'prestamos', component: PrestamosBiblioteca },
      { path: 'devoluciones', component: DevolucionesBiblioteca },
      { path: 'reportes', component: Reportes },
      { path: '', redirectTo: 'catalogo', pathMatch: 'full' }
    ]
  },
  {
    path: 'inicioSecretaria',
    component: InicioSecretaria,
    canActivate: [rolGuard],
    canActivateChild: [rolGuard],
    data: { allowedRoles: [RolUsuario.SECRETARIO] },
    children: [
      { path: 'inscripcion', component: InscripcionSecretaria },
      { path: 'cobros', component: CobrosSecretaria },
      { path: 'facturas', component: FacturasSecretaria },
      { path: 'solvencia', component: SolvenciaSecretaria },
      { path: 'estudiantes', component: ConsultaEstudiantes },
      { path: '', redirectTo: 'cobros', pathMatch: 'full' },
    ],
  },
  {
    path: 'inicioEstudiante',
    component: InicioEstudiante,
    canActivate: [rolGuard],
    canActivateChild: [rolGuard],
    data: { allowedRoles: [RolUsuario.ESTUDIANTE] },
    children: [
      { path: 'perfil', component: PerfilEstudiante },
      { path: 'cursos', component: CursosEstudiante },
      { path: 'notas', component: NotasEstudiante },
      { path: 'solvencia', component: SolvenciaEstudiante },
      { path: 'estadoCuenta', component: EstadoCuentaEstudiante },
      { path: 'biblioteca', component: BibliotecaEstudiante },
      { path: '', redirectTo: 'perfil', pathMatch: 'full' },
    ],
  },
  {
    path: 'inicioMaestro',
    component: InicioMaestro,
    canActivate: [rolGuard],
    canActivateChild: [rolGuard],
    data: { allowedRoles: [RolUsuario.MAESTRO] },
    children: [
      { path: 'cursos', component: CursosMaestro },
      { path: 'notas', component: NotasMaestro },
      { path: 'asistencia', component: ControlAsistenciaMaestro },
      { path: '', redirectTo: 'cursos', pathMatch: 'full' },
    ],
  },
  { path: '**', redirectTo: 'iniciar-sesion' },
  
];
