import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { AsignarMaestroRequest } from '../../../Objetos/objetosRequest/AsignarMaestroRequest';
import { CurriculumCursosGradoRequest } from '../../../Objetos/objetosRequest/CurriculumCursosGradoRequest';
import { Curso } from '../../../Objetos/objetosResponse/Curso';
import { Grado } from '../../../Objetos/objetosResponse/Grado';
import { Seccion } from '../../../Objetos/objetosResponse/Seccion';
import { UsuarioSelectResponse } from '../../../Objetos/objetosResponse/UsuarioSelectResponse';
import { PaginacionLista } from '../../accesorios/paginacionLista/paginacionLista';
import { AsignacionMaestrosSeccionService } from '../../accesorios/servicios/asignacion-maestros-seccion.service';
import { CurriculumCursosGradoService } from '../../accesorios/servicios/curriculum-cursos-grado.service';
import { CursoService } from '../../accesorios/servicios/curso.service';
import { GradoService } from '../../accesorios/servicios/grado.service';
import { SeccionService } from '../../accesorios/servicios/seccion.service';
import { UsuarioService } from '../../accesorios/servicios/usuario.service';

@Component({
  selector: 'app-curriculo-admin',
  imports: [FormsModule, PaginacionLista],
  templateUrl: './curriculoAdmin.html',
})
export class CurriculoAdmin implements OnInit {
  grados: Grado[] = [];
  cursos: Curso[] = [];
  maestros: UsuarioSelectResponse[] = [];
  secciones: Seccion[] = [];
  cursosAsignados: Curso[] = [];
  idGrado = '';
  idCurso = '';
  idSeccion = '';
  idMaestro = '';
  guardando = false;
  mensaje = '';
  error = '';
  paginaCursosAsignados = 0;
  totalPaginasCursosAsignados = 0;
  totalCursosAsignados = 0;

  constructor(
    private readonly gradoService: GradoService,
    private readonly cursoService: CursoService,
    private readonly seccionService: SeccionService,
    private readonly curriculumService: CurriculumCursosGradoService,
    private readonly asignacionService: AsignacionMaestrosSeccionService,
    private readonly usuarioService: UsuarioService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.gradoService.obtenerGrados().subscribe({
      next: (grados) => {
        this.grados = grados;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar los grados.'),
    });
    this.cursoService.obtenerCursosPorEstado('ACTIVO').subscribe({
      next: (cursos) => {
        this.cursos = cursos;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar los cursos.'),
    });
    this.usuarioService.obtenerUsuariosSelect('MAESTRO').subscribe({
      next: (maestros) => {
        this.maestros = maestros;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar los maestros.'),
    });
  }

  cargarDatosGrado(): void {
    this.cursosAsignados = [];
    this.secciones = [];
    this.idSeccion = '';
    if (!this.idGrado) {
      return;
    }
    this.paginaCursosAsignados = 0;
    this.cargarPaginaCursosAsignados();
    this.seccionService.obtenerSeccionesDeGrado(Number(this.idGrado)).subscribe({
      next: (secciones) => {
        this.secciones = secciones;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar las secciones.'),
    });
  }

  private cargarPaginaCursosAsignados(): void {
    if (!this.idGrado) {
      return;
    }
    this.curriculumService.obtenerCursosDeGradoPaginados(
      Number(this.idGrado),
      this.paginaCursosAsignados,
    ).subscribe({
      next: (respuesta) => {
        this.cursosAsignados = respuesta.contenido;
        this.totalPaginasCursosAsignados = respuesta.totalPaginas;
        this.totalCursosAsignados = respuesta.totalElementos;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudo consultar el currículo.'),
    });
  }

  cambiarPaginaCursosAsignados(pagina: number): void {
    this.paginaCursosAsignados = pagina;
    this.cargarPaginaCursosAsignados();
  }

  asignarCurso(): void {
    if (!this.idGrado || !this.idCurso || this.guardando) {
      return;
    }
    this.guardando = true;
    this.curriculumService.asignarCursoAGrado(
      new CurriculumCursosGradoRequest(this.idGrado, this.idCurso),
    ).subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = 'El curso se asignó al grado correctamente.';
        this.idCurso = '';
        this.cargarDatosGrado();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.mostrarError(error, 'No se pudo asignar el curso al grado.');
      },
    });
  }

  asignarMaestro(formulario: NgForm): void {
    if (formulario.invalid || this.guardando) {
      return;
    }
    this.guardando = true;
    this.asignacionService.asignarMaestro(
      new AsignarMaestroRequest(this.idSeccion, this.idMaestro, this.idCurso),
    ).subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = 'El maestro se asignó a la sección y curso.';
        this.idMaestro = '';
        formulario.resetForm();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.mostrarError(error, 'No se pudo asignar el maestro.');
      },
    });
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
