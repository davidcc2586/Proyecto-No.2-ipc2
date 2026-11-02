import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

@Injectable({ providedIn: 'root' })
export class ReportesService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/reportes`;

  constructor(private readonly http: HttpClient) {}

  historialPagos(idEstudiante: number, fechaInicio: string, fechaFin: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/pagos/${idEstudiante}`, {
      params: this.crearParametrosFechas(fechaInicio, fechaFin),
      responseType: 'blob',
    });
  }

  ingresos(fechaInicio: string, fechaFin: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/ingresos`, {
      params: this.crearParametrosFechas(fechaInicio, fechaFin),
      responseType: 'blob',
    });
  }

  morosidadBiblioteca(): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/biblioteca/morosidad`, {
      responseType: 'blob',
    });
  }

  private crearParametrosFechas(fechaInicio: string, fechaFin: string): HttpParams {
    let parametros = new HttpParams();
    if (fechaInicio) {
      parametros = parametros.set('fechaInicio', fechaInicio);
    }
    if (fechaFin) {
      parametros = parametros.set('fechaFin', fechaFin);
    }
    return parametros;
  }
}
