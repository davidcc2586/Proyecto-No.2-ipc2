import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { InicioSesionService } from '../../Paginas/accesorios/servicios/inicio-sesion.service';

export const sessionTokenInterceptor: HttpInterceptorFn = (request, next) => {
  const sessionService = inject(InicioSesionService);
  const router = inject(Router);
  const session = sessionService.getSesion();
  const isLoginRequest = request.url.includes('/inicioSesion/acceso');
  const authorizedRequest = session && !isLoginRequest
    ? request.clone({ setHeaders: { Authorization: `Bearer ${session.accessToken}` } })
    : request;

  return next(authorizedRequest).pipe(
    catchError((error: unknown) => {
      if (error instanceof HttpErrorResponse && error.status === 401 && session) {
        sessionService.cerrarSesion();
        void router.navigate(['/iniciar-sesion']);
      }
      return throwError(() => error);
    }),
  );
};
