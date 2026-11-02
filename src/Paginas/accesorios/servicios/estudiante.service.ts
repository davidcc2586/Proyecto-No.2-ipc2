import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EstudianteActualizarRequest } from '../../../Objetos/objetosRequest/EstudianteActualizarRequest';
import { EstudianteRequest } from '../../../Objetos/objetosRequest/EstudianteRequest';
import { Estudiante } from '../../../Objetos/objetosResponse/Estudiante';
import { EstudianteDatosResponse } from '../../../Objetos/objetosResponse/EstudianteDatosResponse';
import { EstudianteSelectResponse } from '../../../Objetos/objetosResponse/EstudianteSelectResponse';
import { RestConstants } from './rest-constants';
import { PaginaResponse } from '../../../Objetos/objetosResponse/PaginaResponse';

@Injectable({ providedIn: 'root' })
export class EstudianteService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/estudiante`;

  constructor(private readonly http: HttpClient) {}

  obtenerEstudiantesActivos(): Observable<EstudianteSelectResponse[]> {
    return this.obtenerEstudiantesPorEstado('ACTIVO');
  }

  obtenerEstudiantesPorEstado(estado: string): Observable<EstudianteSelectResponse[]> {
    return this.http.get<EstudianteSelectResponse[]>(
      `${this.baseUrl}/selects/${encodeURIComponent(estado)}`,
    );
  }

  obtenerEstudiantesDeSeccion(idSeccion: number): Observable<Estudiante[]> {
    return this.http.get<Estudiante[]>(`${this.baseUrl}/estudiantesSeccion/${idSeccion}`);
  }

  obtenerEstudiantesDeSeccionPaginados(idSeccion: number,pagina: number,tamano = 10,): Observable<PaginaResponse<Estudiante>> {
    return this.http.get<PaginaResponse<Estudiante>>(
      `${this.baseUrl}/estudiantesSeccionPaginados/${idSeccion}`,
      { params: { pagina, tamano } },
    );
  }

  obtenerEstudiantesPorEstadoPaginados(estado: string,pagina: number,tamano = 10,): Observable<PaginaResponse<Estudiante>> {
    return this.http.get<PaginaResponse<Estudiante>>(
      `${this.baseUrl}/estudiantesPaginados/${encodeURIComponent(estado)}`,
      { params: { pagina, tamano } },
    );
  }

  obtenerDatosEstudiante(idEstudiante: number): Observable<EstudianteDatosResponse> {
    return this.http.get<EstudianteDatosResponse>(
      `${this.baseUrl}/datosEstudiante/${idEstudiante}`,
    );
  }

  actualizarEstudiante(estudiante: EstudianteActualizarRequest): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/actualizarEstudiante`, estudiante);
  }

  cambiarEstadoEstudiante(idEstudiante: number, estado: string): Observable<void> {
    return this.http.patch<void>(
      `${this.baseUrl}/cambiaEstadoEstudiante/${idEstudiante}/${encodeURIComponent(estado)}`,
      null,
    );
  }

  crearCuentaEstudiante(estudiante: EstudianteRequest): Observable<void> {
    return this.http.post<void>(
      `${new RestConstants().getAPIUrl()}/inicioSesion/crearCuentaEstudiante`,
      estudiante,
    );
  }
}
