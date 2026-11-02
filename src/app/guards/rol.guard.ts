import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { InicioSesionService } from '../../Paginas/accesorios/servicios/inicio-sesion.service';

export const rolGuard: CanActivateFn = (route) => {
  const router = inject(Router);
  const sesionService = inject(InicioSesionService);
  const sesion = sesionService.getSesion();
  const rutaUsuario = sesion
    ? sesionService.rutaInicio(sesion.rolUsuario)
    : null;

  if (!sesion || !rutaUsuario) {
    sesionService.cerrarSesion();
    return router.createUrlTree(['/iniciar-sesion']);
  }

  const rolesPermitidos = (
    route.data['allowedRoles'] ??
    route.parent?.data['allowedRoles']
  ) as string[] | undefined;
  if (!rolesPermitidos?.length) {
    return router.createUrlTree(['/iniciar-sesion']);
  }

  const rolUsuario = String(sesion.rolUsuario).toUpperCase();
  if (rolesPermitidos.some((rol) => rol.toUpperCase() === rolUsuario)) {
    return true;
  }

  return router.createUrlTree([rutaUsuario]);
};
