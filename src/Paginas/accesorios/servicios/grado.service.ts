import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GradoRequest } from '../../../Objetos/objetosRequest/GradoRequest';
import { GradoActualizarRequest } from '../../../Objetos/objetosRequest/GradoActualizarRequest';
import { Grado } from '../../../Objetos/objetosResponse/Grado';
import { SelectGradosResponse } from '../../../Objetos/objetosResponse/SelectGradosResponse';
import { RestConstants } from './rest-constants';
import { PaginaResponse } from '../../../Objetos/objetosResponse/PaginaResponse';

@Injectable({ providedIn: 'root' })
export class GradoService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/grado`;

  constructor(private readonly http: HttpClient) {}

  obtenerGrados(): Observable<Grado[]> {
    return this.http.get<Grado[]>(`${this.baseUrl}/catalogo`);
  }

  obtenerGradosPaginados(pagina: number, tamano = 10): Observable<PaginaResponse<Grado>> {
    return this.http.get<PaginaResponse<Grado>>(`${this.baseUrl}/catalogoPaginado`, {
      params: { pagina, tamano },
    });
  }

  obtenerGradosSelect(): Observable<SelectGradosResponse[]> {
    return this.http.get<SelectGradosResponse[]>(`${this.baseUrl}/selectGrados`);
  }

  crearGrado(grado: GradoRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/nuevoGrado`, grado);
  }

  actualizarGrado(grado: GradoActualizarRequest): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/actualizar`, grado);
  }

  obtenerGrado(idGrado: number): Observable<Grado> {
    return this.http.get<Grado>(`${this.baseUrl}/datosGrado/${idGrado}`);
  }
}
