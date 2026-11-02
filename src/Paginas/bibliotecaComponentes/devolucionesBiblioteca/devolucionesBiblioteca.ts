import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { PrestamoLibroDevolucionRequest } from '../../../Objetos/objetosRequest/PrestamoLibroDevolucionRequest';
import { PrestamoLibroResponse } from '../../../Objetos/objetosResponse/PrestamoLibroResponse';
import { BibliotecaService } from '../../accesorios/servicios/biblioteca.service';
import { PaginacionLista } from '../../accesorios/paginacionLista/paginacionLista';

@Component({
  selector: 'app-devoluciones-biblioteca',
  imports: [FormsModule, PaginacionLista],
  templateUrl: './devolucionesBiblioteca.html',
})
export class DevolucionesBiblioteca implements OnInit {
  prestamos: PrestamoLibroResponse[] = [];
  prestamoSeleccionado: PrestamoLibroResponse | null = null;
  fechaDevolucion = this.fechaHoy();
  filtroMoroso = false;
  cargando = false;
  guardando = false;
  mensaje = '';
  error = '';
  pagina = 0;
  totalPaginas = 0;
  totalPrestamos = 0;

  constructor(
    private readonly bibliotecaService: BibliotecaService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.cargarPrestamos();
  }

  cargarPrestamos(): void {
    this.cargando = true;
    this.error = '';
    this.bibliotecaService.obtenerPrestamosPaginados('PRESTADO', this.filtroMoroso, this.pagina).subscribe({
      next: (respuesta) => {
        this.prestamos = respuesta.contenido;
        this.totalPaginas = respuesta.totalPaginas;
        this.totalPrestamos = respuesta.totalElementos;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.error = this.mensajeError(error, 'No se pudieron cargar los préstamos.');
        this.changeDetector.markForCheck();
      },
    });
  }

  cambiarPagina(pagina: number): void {
    this.pagina = pagina;
    this.cargarPrestamos();
  }

  cambiarFiltroMoroso(): void {
    this.pagina = 0;
    this.cargarPrestamos();
  }

  seleccionarPrestamo(prestamo: PrestamoLibroResponse): void {
    this.prestamoSeleccionado = prestamo;
    this.fechaDevolucion = this.fechaHoy();
    this.mensaje = '';
    this.error = '';
  }

  registrarDevolucion(formulario: NgForm): void {
    this.error = '';
    this.mensaje = '';
    if (!this.prestamoSeleccionado) {
      this.error = 'Selecciona un préstamo para registrar la devolución.';
      return;
    }
    if (formulario.invalid) {
      formulario.control.markAllAsTouched();
      this.error = 'Selecciona la fecha de devolución.';
      return;
    }

    this.guardando = true;
    const request = new PrestamoLibroDevolucionRequest(
      this.fechaDevolucion,
      String(this.prestamoSeleccionado.idPrestamoLibro),
    );
    this.bibliotecaService.registrarDevolucion(request).subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = 'La devolución se registró correctamente.';
        this.prestamoSeleccionado = null;
        this.pagina = 0;
        this.cargarPrestamos();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.error = this.mensajeError(error, 'No se pudo registrar la devolución.');
        this.changeDetector.markForCheck();
      },
    });
  }

  formatoFecha(fecha: unknown): string {
    if (Array.isArray(fecha)) {
      const [anio, mes, dia] = fecha;
      return `${String(anio).padStart(4, '0')}-${String(mes).padStart(2, '0')}-${String(dia).padStart(2, '0')}`;
    }
    return typeof fecha === 'string' ? fecha : '—';
  }

  private fechaHoy(): string {
    const ahora = new Date();
    return `${ahora.getFullYear()}-${String(ahora.getMonth() + 1).padStart(2, '0')}-${String(ahora.getDate()).padStart(2, '0')}`;
  }

  private mensajeError(error: HttpErrorResponse, alternativo: string): string {
    if (error.status === 0) {
      return 'No se pudo conectar con el backend. Verifica que esté iniciado y que el proxy esté activo.';
    }
    const cuerpo = error.error;
    if (typeof cuerpo === 'string' && cuerpo.trim()) {
      try {
        const json = JSON.parse(cuerpo) as { error?: string; mensaje?: string };
        return json.error || json.mensaje || cuerpo;
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
