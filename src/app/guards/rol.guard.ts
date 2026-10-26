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

  const rutaPermitida = route.data['rutaPermitida'] as string;
  if (rutaUsuario === rutaPermitida) {
    return true;
  }

  return router.createUrlTree([rutaUsuario]);
};
