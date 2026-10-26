import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfiguracionColegio } from '../../../Objetos/objetosResponse/ConfiguracionColegio';
import { RestConstants } from './rest-constants';

@Injectable({ providedIn: 'root' })
export class ConfiguracionSistemaService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/configurarSistema`;

  constructor(private readonly http: HttpClient) {}

  obtenerAniosLectivos(): Observable<ConfiguracionColegio[]> {
    const path = encodeURIComponent('añosLectivos');
    return this.http.get<ConfiguracionColegio[]>(`${this.baseUrl}/${path}`);
  }

  crearAnioLectivo(anio: number): Observable<{ mensaje: string }> {
    return this.http.post<{ mensaje: string }>(
      `${this.baseUrl}/cambiarAnioLectivo/${anio}`,
      null,
    );
  }

  cambiarAnioLectivo(anio: number): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.baseUrl}/cambiarAnioLectivo/${anio}`,
      null,
    );
  }
}
