import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AsignarMaestroRequest } from '../../../Objetos/objetosRequest/AsignarMaestroRequest';
import { RestConstants } from './rest-constants';

@Injectable({ providedIn: 'root' })
export class AsignacionMaestrosSeccionService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/asignacionMaestrosSeccion`;

  constructor(private readonly http: HttpClient) {}

  asignarMaestro(asignacion: AsignarMaestroRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/asignarMaestro`, asignacion);
  }
}
