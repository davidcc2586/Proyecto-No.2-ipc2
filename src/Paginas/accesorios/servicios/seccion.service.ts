import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SeccionActualizarRequest } from '../../../Objetos/objetosRequest/SeccionActualizarRequest';
import { SeccionRequest } from '../../../Objetos/objetosRequest/SeccionRequest';
import { Seccion } from '../../../Objetos/objetosResponse/Seccion';
import { RestConstants } from './rest-constants';

@Injectable({ providedIn: 'root' })
export class SeccionService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/seccion`;

  constructor(private readonly http: HttpClient) {}

  obtenerSeccionesDeGrado(idGrado: number): Observable<Seccion[]> {
    return this.http.get<Seccion[]>(
      `${this.baseUrl}/catalogoSeccion/${idGrado}`,
    );
  }

  crearSeccion(seccion: SeccionRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/nuevoSeccion`, seccion);
  }

  actualizarSeccion(seccion: SeccionActualizarRequest): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/actualizar`, seccion);
  }

  obtenerSeccion(idSeccion: number): Observable<Seccion> {
    return this.http.get<Seccion>(`${this.baseUrl}/datosSeccion/${idSeccion}`);
  }
}
