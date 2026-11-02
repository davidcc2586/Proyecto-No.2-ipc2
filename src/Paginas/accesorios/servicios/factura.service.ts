import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Factura } from '../../../Objetos/objetosResponse/Factura';
import { RestConstants } from './rest-constants';

@Injectable({ providedIn: 'root' })
export class FacturaService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/factura`;

  constructor(private readonly http: HttpClient) {}

  obtenerFacturas(): Observable<Factura[]> {
    return this.http.get<Factura[]>(`${this.baseUrl}/todos`);
  }
}
