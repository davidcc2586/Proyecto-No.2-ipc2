import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CurriculumCursosGradoRequest } from '../../../Objetos/objetosRequest/CurriculumCursosGradoRequest';
import { CurriculumCursosGrado } from '../../../Objetos/objetosResponse/CurriculumCursosGrado';
import { RestConstants } from './rest-constants';

@Injectable({ providedIn: 'root' })
export class CurriculumCursosGradoService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/curriculumCursosGrado`;

  constructor(private readonly http: HttpClient) {}

  obtenerCursosDeGrado(idGrado: number): Observable<CurriculumCursosGrado[]> {
    return this.http.get<CurriculumCursosGrado[]>(
      `${this.baseUrl}/catalogo/${idGrado}`,
    );
  }

  asignarCursoAGrado(asignacion: CurriculumCursosGradoRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/asignarCursoGrado`, asignacion);
  }
}
