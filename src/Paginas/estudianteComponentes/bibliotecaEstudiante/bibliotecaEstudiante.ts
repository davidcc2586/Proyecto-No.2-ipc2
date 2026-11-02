import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { PrestamoLibroResponse } from '../../../Objetos/objetosResponse/PrestamoLibroResponse';
import { Libro } from '../../../Objetos/objetosResponse/Libro';
import { BibliotecaService } from '../../accesorios/servicios/biblioteca.service';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';
import { PaginacionLista } from '../../accesorios/paginacionLista/paginacionLista';

@Component({
  selector: 'app-biblioteca-estudiante',
  imports: [PaginacionLista],
  templateUrl: './bibliotecaEstudiante.html',
})
export class BibliotecaEstudiante implements OnInit {
  prestamos: PrestamoLibroResponse[] = [];
  librosDisponibles: Libro[] = [];
  cargando = false;
  cargandoCatalogo = false;
  error = '';
  errorCatalogo = '';
  pagina = 0;
  totalPaginas = 0;
  totalPrestamos = 0;
  paginaCatalogo = 0;
  totalPaginasCatalogo = 0;
  totalLibrosDisponibles = 0;
  private idEstudiante = 0;

  constructor(
    private readonly bibliotecaService: BibliotecaService,
    private readonly inicioSesionService: InicioSesionService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const idEstudiante = this.inicioSesionService.getSesion()?.idUsuario;
    if (!idEstudiante) {
      this.error = 'No se encontró el estudiante en la sesión.';
      return;
    }
    this.idEstudiante = idEstudiante;
    this.cargarPrestamos();
    this.cargarCatalogo();
  }

  cargarCatalogo(): void {
    this.cargandoCatalogo = true;
    this.errorCatalogo = '';
    this.bibliotecaService.obtenerCatalogoDisponiblePaginado(this.paginaCatalogo).subscribe({
      next: (respuesta) => {
        this.librosDisponibles = respuesta.contenido;
        this.totalPaginasCatalogo = respuesta.totalPaginas;
        this.totalLibrosDisponibles = respuesta.totalElementos;
        this.cargandoCatalogo = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargandoCatalogo = false;
        this.errorCatalogo = this.mensajeError(
          error,
          'No se pudo cargar el catálogo de libros disponibles.',
          'El backend instalado no incluye el catálogo disponible. Despliega el WAR actualizado y reinicia Tomcat.',
        );
        this.changeDetector.markForCheck();
      },
    });
  }

  cargarPrestamos(): void {
    this.cargando = true;
    this.bibliotecaService.obtenerPrestamosEstudiantePaginados('PRESTADO', this.idEstudiante, false, this.pagina).subscribe({
      next: (respuesta) => {
        this.prestamos = respuesta.contenido;
        this.totalPaginas = respuesta.totalPaginas;
        this.totalPrestamos = respuesta.totalElementos;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.error = this.mensajeError(error, 'No se pudieron consultar tus préstamos y multas.');
        this.changeDetector.markForCheck();
      },
    });
  }

  cambiarPagina(pagina: number): void {
    this.pagina = pagina;
    this.cargarPrestamos();
  }

  cambiarPaginaCatalogo(pagina: number): void {
    this.paginaCatalogo = pagina;
    this.cargarCatalogo();
  }

  formatoFecha(fecha: unknown): string {
    if (Array.isArray(fecha) && fecha.length >= 3) {
      return `${fecha[0]}-${String(fecha[1]).padStart(2, '0')}-${String(fecha[2]).padStart(2, '0')}`;
    }
    return typeof fecha === 'string' ? fecha : '—';
  }

  private mensajeError(
    error: HttpErrorResponse,
    alternativo: string,
    error404 = 'El backend instalado no incluye esta consulta. Despliega el WAR actualizado y reinicia Tomcat.',
  ): string {
    if (error.status === 0) return 'No se pudo conectar con el backend.';
    if (error.status === 404) return error404;
    const cuerpo = error.error;
    if (typeof cuerpo === 'string' && cuerpo.trim()) {
      try {
        const detalle = JSON.parse(cuerpo) as { error?: unknown; mensaje?: unknown };
        if (typeof detalle.error === 'string') return detalle.error;
        if (typeof detalle.mensaje === 'string') return detalle.mensaje;
      } catch {
        return cuerpo;
      }
    }
    if (cuerpo && typeof cuerpo === 'object') {
      const detalle = cuerpo as { error?: unknown; mensaje?: unknown };
      if (typeof detalle.error === 'string') return detalle.error;
      if (typeof detalle.mensaje === 'string') return detalle.mensaje;
    }
    return `${alternativo} (HTTP ${error.status}).`;
  }
}
