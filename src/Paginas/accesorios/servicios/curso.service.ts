import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Curso } from '../../../Objetos/objetosResponse/Curso';
import { RestConstants } from './rest-constants';

@Injectable({ providedIn: 'root' })
export class CursoService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/curso`;

  constructor(private readonly http: HttpClient) {}

  obtenerCursosDeMaestro(idMaestro: number): Observable<Curso[]> {
    return this.http.get<Curso[]>(`${this.baseUrl}/cursosMaestro/${idMaestro}`);
  }

  obtenerCursosDeEstudiante(idEstudiante: number): Observable<Curso[]> {
    return this.http.get<Curso[]>(`${this.baseUrl}/cursosEstudiante/${idEstudiante}`);
  }

  crearCurso(nombreCurso: string): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/ingresarNuevocurso`, nombreCurso);
  }

  obtenerCursosPorEstado(estado: string): Observable<Curso[]> {
    return this.http.get<Curso[]>(
      `${this.baseUrl}/cursos/${encodeURIComponent(estado)}`,
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
