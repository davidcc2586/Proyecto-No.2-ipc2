import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PagoRequest } from '../../../Objetos/objetosRequest/PagoRequest';
import { Solvencia } from '../../../Objetos/objetosResponse/Solvencia';
import { RestConstants } from './rest-constants';

@Injectable({ providedIn: 'root' })
export class PagoService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/pago`;

  constructor(private readonly http: HttpClient) {}

  registrarPago(pago: PagoRequest): Observable<{ mensaje?: string }> {
    return this.http.post<{ mensaje?: string }>(`${this.baseUrl}/registrar`, pago);
  }

  obtenerSolvencia(
    idEstudiante: number,
    anio: number,
    mes: number,
  ): Observable<Solvencia> {
    return this.http.get<Solvencia>(
      `${this.baseUrl}/solvenciaEconomica/${idEstudiante}/${anio}/${mes}`,
    );
  }
}
