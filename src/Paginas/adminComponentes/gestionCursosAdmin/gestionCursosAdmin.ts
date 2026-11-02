import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Curso } from '../../../Objetos/objetosResponse/Curso';
import { CursoService } from '../../accesorios/servicios/curso.service';
import { PaginacionLista } from '../../accesorios/paginacionLista/paginacionLista';

@Component({
  selector: 'app-gestion-cursos-admin',
  imports: [FormsModule, PaginacionLista],
  templateUrl: './gestionCursosAdmin.html',
})
export class GestionCursosAdmin implements OnInit {
  cursos: Curso[] = [];
  estado = 'ACTIVO';
  nombreCurso = '';
  cursoSeleccionado: Curso | null = null;
  cargando = false;
  guardando = false;
  mensaje = '';
  error = '';
  pagina = 0;
  totalPaginas = 0;
  totalCursos = 0;

  constructor(
    private readonly cursoService: CursoService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.cargarCursos();
  }

  cargarCursos(): void {
    this.cargando = true;
    this.error = '';
    this.cursoService.obtenerCursosPorEstadoPaginados(this.estado, this.pagina).subscribe({
      next: (respuesta) => {
        this.cursos = respuesta.contenido;
        this.totalPaginas = respuesta.totalPaginas;
        this.totalCursos = respuesta.totalElementos;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.mostrarError(error, 'No se pudieron cargar los cursos.');
      },
    });
  }

  cambiarPagina(pagina: number): void {
    this.pagina = pagina;
    this.cargarCursos();
  }

  cambiarFiltroEstado(): void {
    this.pagina = 0;
    this.cargarCursos();
  }

  guardarCurso(formulario: NgForm): void {
    if (formulario.invalid || this.guardando) {
      return;
    }
    this.guardando = true;
    const solicitud = this.cursoSeleccionado
      ? this.cursoService.actualizarCurso(this.cursoSeleccionado.idCurso, this.nombreCurso)
      : this.cursoService.crearCurso(this.nombreCurso);

    solicitud.subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = this.cursoSeleccionado
          ? 'El curso se actualizó correctamente.'
          : 'El curso se registró correctamente.';
        this.pagina = 0;
        this.cursoSeleccionado = null;
        this.nombreCurso = '';
        formulario.resetForm();
        this.cargarCursos();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.mostrarError(error, 'No se pudo guardar el curso.');
      },
    });
  }

  seleccionarCurso(curso: Curso): void {
    this.cursoSeleccionado = curso;
    this.nombreCurso = curso.nombreCurso;
    this.mensaje = '';
  }

  cambiarEstado(curso: Curso): void {
    this.cursoService.cambiarEstadoCurso(curso.idCurso).subscribe({
      next: () => {
        this.mensaje = 'El estado del curso se cambió correctamente.';
        this.pagina = 0;
        this.cargarCursos();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudo cambiar el estado del curso.'),
    });
  }

  cancelarEdicion(): void {
    this.cursoSeleccionado = null;
    this.nombreCurso = '';
  }

  private mostrarError(respuesta: HttpErrorResponse, alternativo: string): void {
    const detalle = respuesta.error;
    this.error =
      typeof detalle === 'object' && detalle !== null && 'error' in detalle &&
      typeof detalle.error === 'string'
        ? detalle.error
        : alternativo;
    this.changeDetector.markForCheck();
  }
}
