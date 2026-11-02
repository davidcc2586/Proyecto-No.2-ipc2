import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Curso } from '../../../Objetos/objetosResponse/Curso';
import { RestConstants } from './rest-constants';
import { PaginaResponse } from '../../../Objetos/objetosResponse/PaginaResponse';

@Injectable({ providedIn: 'root' })
export class CursoService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/curso`;

  constructor(private readonly http: HttpClient) {}

  obtenerCursosDeMaestro(idMaestro: number): Observable<Curso[]> {
    return this.http.get<Curso[]>(`${this.baseUrl}/cursosMaestro/${idMaestro}`);
  }

  obtenerCursosDeMaestroPaginados(idMaestro: number, pagina: number,tamano = 10): Observable<PaginaResponse<Curso>> {
    return this.http.get<PaginaResponse<Curso>>(
      `${this.baseUrl}/cursosMaestroPaginados/${idMaestro}`,
      { params: { pagina, tamano } },
    );
  }

  obtenerCursosDeEstudiante(idEstudiante: number): Observable<Curso[]> {
    return this.http.get<Curso[]>(`${this.baseUrl}/cursosEstudiante/${idEstudiante}`);
  }

  obtenerCursosDeEstudiantePaginados(idEstudiante: number,pagina: number, tamano = 10,): Observable<PaginaResponse<Curso>> {
    return this.http.get<PaginaResponse<Curso>>(
      `${this.baseUrl}/cursosEstudiantePaginados/${idEstudiante}`,
      { params: { pagina, tamano } },
    );
  }

  crearCurso(nombreCurso: string): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/ingresarNuevocurso`,JSON.stringify(nombreCurso),
      { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) },
    );
  }

  obtenerCursosPorEstado(estado: string): Observable<Curso[]> {
    return this.http.get<Curso[]>(
      `${this.baseUrl}/cursos/${encodeURIComponent(estado)}`,
    );
  }

  obtenerCursosPorEstadoPaginados(estado: string,pagina: number,tamano = 10,): Observable<PaginaResponse<Curso>> {
    return this.http.get<PaginaResponse<Curso>>(
      `${this.baseUrl}/cursosPaginados/${encodeURIComponent(estado)}`,
      { params: { pagina, tamano } },
    );
  }

  cambiarEstadoCurso(idCurso: number): Observable<void> {
    return this.http.patch<void>(
      `${this.baseUrl}/activarDesactivarCurso/${idCurso}`,
      null,
    );
  }

  actualizarCurso(idCurso: number, nuevoNombre: string): Observable<void> {
    return this.http.patch<void>(
      `${this.baseUrl}/activarDesactivarCurso/${idCurso}/${encodeURIComponent(nuevoNombre)}`,
      null,
    );
  }

  obtenerCurso(idCurso: number): Observable<Curso> {
    return this.http.get<Curso>(`${this.baseUrl}/datosCurso/${idCurso}`);
  }
}
