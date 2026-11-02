import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RecuperarClaveRequest } from '../../../Objetos/objetosRequest/RecuperarClaveRequest';
import { InicioSesion } from '../../../Objetos/objetosResponse/InicioSesion';
import { RecuperarClaveResponse } from '../../../Objetos/objetosResponse/RecuperarClaveResponse';

@Injectable({ providedIn: 'root' })
export class InicioSesionService {
  private readonly baseUrl = '/Proyecto_2_ipc2/api/inicioSesion';
  private readonly claveSesion = 'sesionUsuario';

  constructor(private readonly http: HttpClient) {}

  iniciarSesion(usuario: string, clave: string): Observable<InicioSesion> {
    return this.http.post<InicioSesion>(`${this.baseUrl}/acceso`, { usuario, clave });
  }

  recuperarClave(datos: RecuperarClaveRequest): Observable<RecuperarClaveResponse> {
    return this.http.post<RecuperarClaveResponse>(
      `${this.baseUrl}/recuperarContraseña`,
      datos,
    );
  }

  setSesion(sesion: InicioSesion): void {
    sessionStorage.setItem(this.claveSesion, JSON.stringify(sesion));
  }

  getSesion(): InicioSesion | null {
    const sesionGuardada = sessionStorage.getItem(this.claveSesion);
    if (!sesionGuardada) {
      return null;
    }

    try {
      const sesion: unknown = JSON.parse(sesionGuardada);
      if (
        typeof sesion !== 'object' ||
        sesion === null ||
        !('idUsuario' in sesion) ||
        !('usuario' in sesion) ||
        !('rolUsuario' in sesion) ||
        !('anioLectivo' in sesion) ||
        !('accessToken' in sesion) ||
        !('expiresAt' in sesion) ||
        typeof sesion.idUsuario !== 'number' ||
        typeof sesion.usuario !== 'string' ||
        typeof sesion.rolUsuario !== 'string' ||
        typeof sesion.anioLectivo !== 'number' ||
        typeof sesion.accessToken !== 'string' ||
        !sesion.accessToken ||
        typeof sesion.expiresAt !== 'number' ||
        sesion.expiresAt <= Math.floor(Date.now() / 1000)
      ) {
        this.cerrarSesion();
        return null;
      }

      return sesion as InicioSesion;
    } catch (error) {
      console.error('No se pudo leer la sesión guardada.', error);
      this.cerrarSesion();
      return null;
    }
  }

  cerrarSesion(): void {
    sessionStorage.removeItem(this.claveSesion);
  }

  rutaInicio(rolUsuario: string): string | null {
    switch (this.normalizarRol(rolUsuario)) {
      case 'SUPERADMIN':
        return '/inicioSuperAdmin';
      case 'ADMIN':
        return '/inicioAdmin';
      case 'BIBLIOTECARIO':
        return '/inicioBibliotecario';
      case 'SECRETARIO':
        return '/inicioSecretaria';
      case 'MAESTRO':
        return '/inicioMaestro';
      case 'ESTUDIANTE':
        return '/inicioEstudiante';
      default:
        return null;
    }
  }

  private normalizarRol(rolUsuario: string): string {
    return rolUsuario.normalize('NFD').replace(/\p{Diacritic}/gu, '').replace(/[^a-zA-Z]/g, '').toUpperCase();
  }
}
