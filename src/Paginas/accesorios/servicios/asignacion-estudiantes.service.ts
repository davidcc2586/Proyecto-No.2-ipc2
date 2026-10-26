import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AsignacionEstudianteRequest } from '../../../Objetos/objetosRequest/AsignacionEstudianteRequest';
import { RestConstants } from './rest-constants';

@Injectable({ providedIn: 'root' })
export class AsignacionEstudiantesService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/asignacionEstudiantes`;

  constructor(private readonly http: HttpClient) {}

  asignarEstudiante(asignacion: AsignacionEstudianteRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/asignarEstudiante`, asignacion);
  }
}
