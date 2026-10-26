import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LibroActualizarRequest } from '../../../Objetos/objetosRequest/LibroActualizarRequest';
import { LibroRequest } from '../../../Objetos/objetosRequest/LibroRequest';
import { PrestamoLibroDevolucionRequest } from '../../../Objetos/objetosRequest/PrestamoLibroDevolucionRequest';
import { PrestamoLibroRequest } from '../../../Objetos/objetosRequest/PrestamoLibroRequest';
import { Libro } from '../../../Objetos/objetosResponse/Libro';
import { LibroSelectResponse } from '../../../Objetos/objetosResponse/LibroSelectResponse';
import { PrestamoLibroResponse } from '../../../Objetos/objetosResponse/PrestamoLibroResponse';
import { RestConstants } from './rest-constants';

@Injectable({ providedIn: 'root' })
export class BibliotecaService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/biblioteca`;

  constructor(private readonly http: HttpClient) {}

  obtenerCatalogo(): Observable<Libro[]> {
    return this.http.get<Libro[]>(`${this.baseUrl}/catalogo`);
  }

  obtenerLibro(idLibro: number): Observable<Libro> {
    return this.http.get<Libro>(`${this.baseUrl}/datosLibro/${idLibro}`);
  }

  obtenerLibrosSelect(): Observable<LibroSelectResponse[]> {
    return this.http.get<LibroSelectResponse[]>(`${this.baseUrl}/selects`);
  }

  registrarLibro(libro: LibroRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/registraLibro`, libro);
  }

  eliminarLibro(idLibro: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/eliminarLibro/${idLibro}`);
  }

  cambiarEstadoLibro(idLibro: number): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/desactivarActivar/${idLibro}`, null);
  }

  actualizarLibro(libro: LibroActualizarRequest): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/actualizar`, libro);
  }

  obtenerPrestamos(
    estado: string,
    filtroMoroso: boolean,
  ): Observable<PrestamoLibroResponse[]> {
    return this.http.get<PrestamoLibroResponse[]>(
      `${this.baseUrl}/prestamoLibro/${encodeURIComponent(estado)}/${filtroMoroso}`,
    );
  }

  registrarPrestamo(prestamo: PrestamoLibroRequest): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/RegistrarPrestamo`, prestamo);
  }

  registrarDevolucion(
    devolucion: PrestamoLibroDevolucionRequest,
  ): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/RegistrarDevolucio`, devolucion);
  }
}
