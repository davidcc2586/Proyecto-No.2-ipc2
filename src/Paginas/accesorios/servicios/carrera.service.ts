import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Carrera } from '../../../Objetos/objetosResponse/Carrera';
import { CarreraActualizarRequest } from '../../../Objetos/objetosResponse/CarreraActualizarRequest';
import { RestConstants } from './rest-constants';
import { PaginaResponse } from '../../../Objetos/objetosResponse/PaginaResponse';

@Injectable({ providedIn: 'root' })
export class CarreraService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/carrera`;

  constructor(private readonly http: HttpClient) {}

  obtenerCarreras(): Observable<Carrera[]> {
    return this.http.get<Carrera[]>(`${this.baseUrl}/catalogo`);
  }

  obtenerCarrerasPaginadas(pagina: number, tamano = 10): Observable<PaginaResponse<Carrera>> {
    return this.http.get<PaginaResponse<Carrera>>(`${this.baseUrl}/catalogoPaginado`, {
      params: { pagina, tamano },
    });
  }

  obtenerCarrera(idCarrera: number): Observable<Carrera> {
    return this.http.get<Carrera>(`${this.baseUrl}/catalogo/${idCarrera}`);
  }

  crearCarrera(nombreCarrera: string): Observable<void> {
    return this.http.post<void>(
      `${this.baseUrl}/nuevoCarrera`,
      JSON.stringify(nombreCarrera),
      { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) },
    );
  }

  actualizarCarrera(carrera: CarreraActualizarRequest): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/actualizar`, carrera);
  }

  cambiarEstadoCarrera(idCarrera: number): Observable<void> {
    return this.http.put<void>(
      `${this.baseUrl}/activarDesactivarCarrera/${idCarrera}`,
      null,
    );
  }
}
