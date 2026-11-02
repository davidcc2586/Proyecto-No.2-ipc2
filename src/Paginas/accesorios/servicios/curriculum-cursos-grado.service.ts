import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CurriculumCursosGradoRequest } from '../../../Objetos/objetosRequest/CurriculumCursosGradoRequest';
import { CurriculumCursosGrado } from '../../../Objetos/objetosResponse/CurriculumCursosGrado';
import { RestConstants } from './rest-constants';
import { PaginaResponse } from '../../../Objetos/objetosResponse/PaginaResponse';
import { Curso } from '../../../Objetos/objetosResponse/Curso';

@Injectable({ providedIn: 'root' })
export class CurriculumCursosGradoService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/curriculumCursosGrado`;

  constructor(private readonly http: HttpClient) {}

  obtenerCursosDeGrado(idGrado: number): Observable<CurriculumCursosGrado[]> {
    return this.http.get<CurriculumCursosGrado[]>(
      `${this.baseUrl}/catalogo/${idGrado}`,
    );
  }

  obtenerCursosDeGradoPaginados(idGrado: number,pagina: number,tamano = 10,): Observable<PaginaResponse<Curso>> {
    return this.http.get<PaginaResponse<Curso>>(`${this.baseUrl}/catalogoPaginado/${idGrado}`,{ params: { pagina, tamano } },);
  }

  asignarCursoAGrado(asignacion: CurriculumCursosGradoRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/asignarCursoGrado`, asignacion);
  }
}
