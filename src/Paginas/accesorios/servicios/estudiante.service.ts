import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EstudianteSelectResponse } from '../../../Objetos/objetosResponse/EstudianteSelectResponse';
import { RestConstants } from './rest-constants';

@Injectable({ providedIn: 'root' })
export class EstudianteService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/estudiante`;

  constructor(private readonly http: HttpClient) {}

  obtenerEstudiantesActivos(): Observable<EstudianteSelectResponse[]> {
    return this.http.get<EstudianteSelectResponse[]>(`${this.baseUrl}/selects/ACTIVO`);
  }
}
