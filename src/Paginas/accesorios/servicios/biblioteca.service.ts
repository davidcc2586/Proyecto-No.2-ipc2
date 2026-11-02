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
import { PaginaResponse } from '../../../Objetos/objetosResponse/PaginaResponse';

@Injectable({ providedIn: 'root' })
export class BibliotecaService {
  private readonly baseUrl = `${new RestConstants().getAPIUrl()}/biblioteca`;

  constructor(private readonly http: HttpClient) {}

  obtenerCatalogo(): Observable<Libro[]> {
    return this.http.get<Libro[]>(`${this.baseUrl}/catalogo`);
  }

  obtenerCatalogoPaginado(pagina: number, tamano = 10): Observable<PaginaResponse<Libro>> {
    return this.http.get<PaginaResponse<Libro>>(`${this.baseUrl}/catalogoPaginado`, {
      params: { pagina, tamano },
    });
  }

  obtenerCatalogoDisponiblePaginado(pagina: number, tamano = 10): Observable<PaginaResponse<Libro>> {
    return this.http.get<PaginaResponse<Libro>>(`${this.baseUrl}/catalogoDisponiblePaginado`, {
      params: { pagina, tamano },
    });
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

  obtenerPrestamosPaginados(
    estado: string,
    filtroMoroso: boolean,
    pagina: number,
    tamano = 10,
  ): Observable<PaginaResponse<PrestamoLibroResponse>> {
    return this.http.get<PaginaResponse<PrestamoLibroResponse>>(
      `${this.baseUrl}/prestamoLibroPaginado/${encodeURIComponent(estado)}/${filtroMoroso}`,
      { params: { pagina, tamano } },
    );
  }

  obtenerPrestamosEstudiante(
    estado: string,
    idEstudiante: number,
    filtroMoroso: boolean,
  ): Observable<PrestamoLibroResponse[]> {
    return this.http.get<PrestamoLibroResponse[]>(
      `${this.baseUrl}/prestamoLibroEstudiante/${encodeURIComponent(estado)}/${idEstudiante}/${filtroMoroso}`,
    );
  }

  obtenerPrestamosEstudiantePaginados(
    estado: string,
    idEstudiante: number,
    filtroMoroso: boolean,
    pagina: number,
    tamano = 10,
  ): Observable<PaginaResponse<PrestamoLibroResponse>> {
    return this.http.get<PaginaResponse<PrestamoLibroResponse>>(
      `${this.baseUrl}/prestamoLibroEstudiantePaginado/${encodeURIComponent(estado)}/${idEstudiante}/${filtroMoroso}`,
      { params: { pagina, tamano } },
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
