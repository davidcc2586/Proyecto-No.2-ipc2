import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AsignarMaestroRequest } from '../../../Objetos/objetosRequest/AsignarMaestroRequest';
import { AsignacionMaestroResponse } from '../../../Objetos/objetosResponse/AsignacionMaestroResponse';
import { RestConstants } from './rest-constants';
import { PaginaResponse } from '../../../Objetos/objetosResponse/PaginaResponse';

@Injectable({ providedIn: 'root' })
export class AsignacionMaestrosSeccionService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/asignacionMaestrosSeccion`;

  constructor(private readonly http: HttpClient) {}

  asignarMaestro(asignacion: AsignarMaestroRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/asignarMaestro`, asignacion);
  }

  obtenerAsignacionesMaestro(idMaestro: number): Observable<AsignacionMaestroResponse[]> {
    return this.http.get<AsignacionMaestroResponse[]>(
      `${this.baseUrl}/asignacionesMaestro/${idMaestro}`,
    );
  }

  obtenerAsignacionesMaestroPaginadas(
    idMaestro: number,
    pagina: number,
    tamano = 10,
  ): Observable<PaginaResponse<AsignacionMaestroResponse>> {
    return this.http.get<PaginaResponse<AsignacionMaestroResponse>>(
      `${this.baseUrl}/asignacionesMaestroPaginadas/${idMaestro}`,
      { params: { pagina, tamano } },
    );
  }
}
