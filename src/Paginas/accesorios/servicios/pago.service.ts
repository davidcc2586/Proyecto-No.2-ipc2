import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PagoRequest } from '../../../Objetos/objetosRequest/PagoRequest';
import { Solvencia } from '../../../Objetos/objetosResponse/Solvencia';
import { EstadoCuentaResponse } from '../../../Objetos/objetosResponse/EstadoCuentaResponse';
import { RestConstants } from './rest-constants';

@Injectable({ providedIn: 'root' })
export class PagoService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/pago`;

  constructor(private readonly http: HttpClient) {}

  registrarPago(pago: PagoRequest): Observable<{ mensaje?: string; idFactura: number }> {
    return this.http.post<{ mensaje?: string; idFactura: number }>(`${this.baseUrl}/registrar`, pago);
  }

  obtenerSolvencia(idEstudiante: number,anio: number,mes: number,): Observable<Solvencia> {
    return this.http.get<Solvencia>(
      `${this.baseUrl}/solvenciaEconomica/${idEstudiante}/${anio}/${mes}`,
    );
  }

  obtenerEstadoCuenta(idEstudiante: number): Observable<EstadoCuentaResponse> {
    return this.http.get<EstadoCuentaResponse>(`${this.baseUrl}/estadoCuenta/${idEstudiante}`);
  }
}
