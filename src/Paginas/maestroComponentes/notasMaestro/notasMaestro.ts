import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActividadRequest } from '../../../Objetos/objetosRequest/ActividadRequest';
import { NotaRequest } from '../../../Objetos/objetosRequest/NotaRequest';
import { TipoActividad } from '../../../Objetos/enums/TipoActividad';
import { Actividad } from '../../../Objetos/objetosResponse/Actividad';
import { AsignacionMaestroResponse } from '../../../Objetos/objetosResponse/AsignacionMaestroResponse';
import { Curso } from '../../../Objetos/objetosResponse/Curso';
import { Estudiante } from '../../../Objetos/objetosResponse/Estudiante';
import { NotaActividad } from '../../../Objetos/objetosResponse/NotaActividad';
import { AsignacionMaestrosSeccionService } from '../../accesorios/servicios/asignacion-maestros-seccion.service';
import { CursoService } from '../../accesorios/servicios/curso.service';
import { EstudianteService } from '../../accesorios/servicios/estudiante.service';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';
import { NotasService } from '../../accesorios/servicios/notas.service';
import { PaginacionLista } from '../../accesorios/paginacionLista/paginacionLista';

@Component({
  selector: 'app-notas-maestro',
  imports: [FormsModule, PaginacionLista],
  templateUrl: './notasMaestro.html',
})
export class NotasMaestro implements OnInit {
  readonly tiposActividad = [
    { key: 'PARCIAL', nombre: TipoActividad.PARCIAL },
    { key: 'ACTIVIDAD', nombre: TipoActividad.ACTIVIDAD },
    { key: 'ZONA', nombre: TipoActividad.ZONA },
    { key: 'EXAMEN_FINAL', nombre: TipoActividad.EXAMEN_FINAL },
  ];
  cursos: Curso[] = [];
  asignaciones: AsignacionMaestroResponse[] = [];
  actividades: Actividad[] = [];
  estudiantes: Estudiante[] = [];
  notas: NotaActividad[] = [];
  idAsignacion = '';
  idCurso = '';
  idGrado = '';
  idSeccion = '';
  idActividad = '';
  actividadForm = { nombre: '', ponderacion: '', tipo: 'ACTIVIDAD' };
  punteos: Record<number, string> = {};
  cargando = false;
  guardando = false;
  mensaje = '';
  error = '';
  pagina = 0;
  totalPaginas = 0;
  totalEstudiantes = 0;

  constructor(
    private readonly cursoService: CursoService,
    private readonly asignacionService: AsignacionMaestrosSeccionService,
    private readonly estudianteService: EstudianteService,
    private readonly notasService: NotasService,
    private readonly inicioSesionService: InicioSesionService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const idMaestro = this.inicioSesionService.getSesion()?.idUsuario;
    if (!idMaestro) {
      this.error = 'No se encontró el maestro en la sesión.';
      return;
    }
    this.asignacionService.obtenerAsignacionesMaestro(idMaestro).subscribe({
      next: (asignaciones) => {
        this.asignaciones = asignaciones;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar las asignaciones del maestro.'),
    });
    this.cursoService.obtenerCursosDeMaestro(idMaestro).subscribe({
      next: (cursos) => {
        this.cursos = cursos;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar los cursos.'),
    });
  }

  cambiarAsignacion(): void {
    const asignacion = this.asignacionSeleccionada();
    this.idCurso = String(asignacion?.idCurso ?? '');
    this.idSeccion = String(asignacion?.idSeccion ?? '');
    this.idActividad = '';
    this.actividades = [];
    this.estudiantes = [];
    this.notas = [];
    this.pagina = 0;
    this.cargarActividades();
  }

  cargarActividades(): void {
    this.actividades = [];
    this.estudiantes = [];
    this.notas = [];
    if (!this.idCurso || !this.idSeccion) {
      return;
    }
    this.cargando = true;
    this.notasService.obtenerActividades(Number(this.idCurso), Number(this.idSeccion)).subscribe({
      next: (actividades) => {
        this.actividades = actividades;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.mostrarError(error, 'No se pudieron cargar las actividades.');
      },
    });
    this.cargarEstudiantesPagina();
  }

  registrarActividad(formulario: NgForm): void {
    if (formulario.invalid || this.guardando || !this.idCurso || !this.idSeccion) {
      return;
    }
    this.guardando = true;
    const actividad = new ActividadRequest(
      this.idSeccion,
      this.idCurso,
      this.actividadForm.nombre,
      this.actividadForm.ponderacion,
      this.actividadForm.tipo,
    );
    this.notasService.registrarActividad(actividad).subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = 'La actividad se registró correctamente.';
        this.actividadForm = { nombre: '', ponderacion: '', tipo: 'ACTIVIDAD' };
        formulario.resetForm();
        this.cargarActividades();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.mostrarError(error, 'No se pudo registrar la actividad.');
      },
    });
  }

  cargarNotas(): void {
    this.notas = [];
    if (!this.idActividad) {
      return;
    }
    this.notasService.obtenerNotasPaginadas(Number(this.idActividad), this.pagina).subscribe({
      next: (respuesta) => {
        this.notas = respuesta.contenido;
        this.punteos = {};
        for (const nota of respuesta.contenido) {
          this.punteos[nota.idEstudiante] = String(nota.punteoObtenido);
        }
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron consultar las notas.'),
    });
  }

  cambiarActividad(): void {
    this.pagina = 0;
    this.cargarEstudiantesPagina();
    this.cargarNotas();
  }

  cambiarPagina(pagina: number): void {
    this.pagina = pagina;
    this.cargarEstudiantesPagina();
    this.cargarNotas();
  }

  private cargarEstudiantesPagina(): void {
    if (!this.idSeccion) {
      return;
    }
    this.estudianteService.obtenerEstudiantesDeSeccionPaginados(
      Number(this.idSeccion),
      this.pagina,
    ).subscribe({
      next: (respuesta) => {
        this.estudiantes = respuesta.contenido;
        this.totalPaginas = respuesta.totalPaginas;
        this.totalEstudiantes = respuesta.totalElementos;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) =>
        this.mostrarError(error, 'No se pudieron cargar los estudiantes de la sección.'),
    });
  }

  guardarNota(estudiante: Estudiante): void {
    const punteo = this.punteos[estudiante.idEstudiante];
    if (punteo === undefined || punteo === '') {
      this.error = 'Ingresa un punteo antes de guardar.';
      return;
    }
    this.guardando = true;
    this.notasService.modificarNota(
      new NotaRequest(this.idActividad, String(estudiante.idEstudiante), punteo),
    ).subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = `La nota de ${estudiante.nombre} se guardó correctamente.`;
        this.cargarNotas();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.mostrarError(error, 'No se pudo guardar la nota.');
      },
    });
  }

  notaEstudiante(idEstudiante: number): string {
    return String(this.notas.find((nota) => nota.idEstudiante === idEstudiante)?.punteoObtenido ?? '');
  }

  ponderacionActividadSeleccionada(): number {
    return this.actividades.find(
      (actividad) => actividad.idActividad === Number(this.idActividad),
    )?.ponderacion ?? 100;
  }

  textoAsignacion(asignacion: AsignacionMaestroResponse): string {
    const nombreCurso = asignacion.nombreCurso ??
      this.cursos.find((curso) => curso.idCurso === asignacion.idCurso)?.nombreCurso ??
      `Curso ${asignacion.idCurso}`;
    const nombreSeccion = asignacion.nombreSeccion ??
      asignacion.seccion ??
      `Sección ${asignacion.idSeccion}`;
    const anio = asignacion.anio ? ` · ${asignacion.anio}` : '';
    return `${nombreCurso} · ${nombreSeccion}${anio}`;
  }

  private asignacionSeleccionada(): AsignacionMaestroResponse | undefined {
    return this.asignaciones.find(
      (asignacion) => `${asignacion.idCurso}-${asignacion.idSeccion}` === this.idAsignacion,
    );
  }

  private mostrarError(respuesta: HttpErrorResponse, alternativo: string): void {
    const detalle = respuesta.error;
    this.error =
      typeof detalle === 'object' && detalle !== null && 'error' in detalle &&
      typeof detalle.error === 'string'
        ? detalle.error
        : respuesta.status === 0
          ? 'No se pudo conectar con el backend.'
          : alternativo;
    this.changeDetector.markForCheck();
  }
}
