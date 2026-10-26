import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ActividadRequest } from '../../../Objetos/objetosRequest/ActividadRequest';
import { NotaRequest } from '../../../Objetos/objetosRequest/NotaRequest';
import { Actividad } from '../../../Objetos/objetosResponse/Actividad';
import { NotaActividad } from '../../../Objetos/objetosResponse/NotaActividad';
import { RestConstants } from './rest-constants';

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

  obtenerNotas(idActividad: number): Observable<NotaActividad[]> {
    return this.http.get<NotaActividad[]>(`${this.baseUrl}/notasCurso/${idActividad}`);
  }

  modificarNota(nota: NotaRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/modificarNota`, nota);
  }
}
