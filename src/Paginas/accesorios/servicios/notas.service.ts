import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ActividadRequest } from '../../../Objetos/objetosRequest/ActividadRequest';
import { NotaRequest } from '../../../Objetos/objetosRequest/NotaRequest';
import { NotaHistorialResponse } from '../../../Objetos/objetosResponse/NotaHistorialResponse';
import { Actividad } from '../../../Objetos/objetosResponse/Actividad';
import { NotaActividad } from '../../../Objetos/objetosResponse/NotaActividad';
import { RestConstants } from './rest-constants';
import { PaginaResponse } from '../../../Objetos/objetosResponse/PaginaResponse';

@Injectable({ providedIn: 'root' })
export class NotasService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/notas`;

  constructor(private readonly http: HttpClient) {}

  registrarActividad(actividad: ActividadRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/registrarActividad`, actividad);
  }

  obtenerActividades(idCurso: number, idSeccion: number): Observable<Actividad[]> {
    return this.http.get<Actividad[]>(
      `${this.baseUrl}/actividades/${idCurso}/${idSeccion}`,
    );
  }

  obtenerActividadesPaginadas(idCurso: number,idSeccion: number,pagina: number,tamano = 10,): Observable<PaginaResponse<Actividad>> {
    return this.http.get<PaginaResponse<Actividad>>(
      `${this.baseUrl}/actividadesPaginadas/${idCurso}/${idSeccion}`,
      { params: { pagina, tamano } },
    );
  }

  obtenerNotas(idActividad: number): Observable<NotaActividad[]> {
    return this.http.get<NotaActividad[]>(`${this.baseUrl}/notasCurso/${idActividad}`);
  }

  obtenerNotasPaginadas(idActividad: number,pagina: number,tamano = 10,): Observable<PaginaResponse<NotaActividad>> {
    return this.http.get<PaginaResponse<NotaActividad>>(
      `${this.baseUrl}/notasCursoPaginadas/${idActividad}`,
      { params: { pagina, tamano } },
    );
  }

  modificarNota(nota: NotaRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/modificarNota`, nota);
  }

  obtenerHistorialEstudiante(idEstudiante: number): Observable<NotaHistorialResponse[]> {
    return this.http.get<NotaHistorialResponse[]>(`${this.baseUrl}/historialEstudiante/${idEstudiante}`);
  }
}
