import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { PrestamoLibroRequest } from '../../../Objetos/objetosRequest/PrestamoLibroRequest';
import { EstudianteSelectResponse } from '../../../Objetos/objetosResponse/EstudianteSelectResponse';
import { LibroSelectResponse } from '../../../Objetos/objetosResponse/LibroSelectResponse';
import { PrestamoLibroResponse } from '../../../Objetos/objetosResponse/PrestamoLibroResponse';
import { BibliotecaService } from '../../accesorios/servicios/biblioteca.service';
import { EstudianteService } from '../../accesorios/servicios/estudiante.service';
import { PaginacionLista } from '../../accesorios/paginacionLista/paginacionLista';

@Component({
  selector: 'app-prestamos-biblioteca',
  imports: [FormsModule, PaginacionLista],
  templateUrl: './prestamosBiblioteca.html',
})
export class PrestamosBiblioteca implements OnInit {
  libros: LibroSelectResponse[] = [];
  estudiantes: EstudianteSelectResponse[] = [];
  prestamos: PrestamoLibroResponse[] = [];
  idLibro = '';
  idEstudiante = '';
  costoDia = '';
  fechaPrestamo = this.fechaHoy();
  fechaEstimadaDevolucion = '';
  filtroMoroso = false;
  cargando = false;
  cargandoEstudiantes = false;
  guardando = false;
  mensaje = '';
  error = '';
  pagina = 0;
  totalPaginas = 0;
  totalPrestamos = 0;

  constructor(
    private readonly bibliotecaService: BibliotecaService,
    private readonly estudianteService: EstudianteService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.cargarLibros();
    this.cargarEstudiantes();
    this.cargarPrestamos();
  }

  cargarLibros(): void {
    this.bibliotecaService.obtenerLibrosSelect().subscribe({
      next: (libros) => {
        this.libros = libros;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.error = this.mensajeError(error, 'No se pudieron cargar los libros.');
        this.changeDetector.markForCheck();
      },
    });
  }

  cargarEstudiantes(): void {
    this.cargandoEstudiantes = true;
    this.estudianteService.obtenerEstudiantesActivos().subscribe({
      next: (estudiantes) => {
        this.estudiantes = estudiantes;
        this.cargandoEstudiantes = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargandoEstudiantes = false;
        this.error = this.mensajeError(error, 'No se pudieron cargar los estudiantes activos.');
        this.changeDetector.markForCheck();
      },
    });
  }

  cargarPrestamos(): void {
    this.cargando = true;
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

  registrarPrestamo(formulario: NgForm): void {
    this.error = '';
    this.mensaje = '';
    if (formulario.invalid) {
      formulario.control.markAllAsTouched();
      this.error = 'Completa todos los campos obligatorios para registrar el préstamo.';
      return;
    }

    this.guardando = true;
    const request = new PrestamoLibroRequest(
      this.idLibro,
      this.idEstudiante,
      this.costoDia,
      this.fechaPrestamo,
      this.fechaEstimadaDevolucion,
    );
    this.bibliotecaService.registrarPrestamo(request).subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = 'El préstamo se registró correctamente.';
        formulario.resetForm();
        this.idLibro = '';
        this.idEstudiante = '';
        this.costoDia = '';
        this.fechaPrestamo = this.fechaHoy();
        this.fechaEstimadaDevolucion = '';
        this.pagina = 0;
        this.cargarPrestamos();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.error = this.mensajeError(error, 'No se pudo registrar el préstamo.');
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
    const mes = String(ahora.getMonth() + 1).padStart(2, '0');
    const dia = String(ahora.getDate()).padStart(2, '0');
    return `${ahora.getFullYear()}-${mes}-${dia}`;
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
