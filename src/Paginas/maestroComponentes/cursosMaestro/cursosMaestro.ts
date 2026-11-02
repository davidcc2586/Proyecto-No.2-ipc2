import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Curso } from '../../../Objetos/objetosResponse/Curso';
import { AsignacionMaestroResponse } from '../../../Objetos/objetosResponse/AsignacionMaestroResponse';
import { AsignacionMaestrosSeccionService } from '../../accesorios/servicios/asignacion-maestros-seccion.service';
import { CursoService } from '../../accesorios/servicios/curso.service';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';
import { PaginacionLista } from '../../accesorios/paginacionLista/paginacionLista';

@Component({
  selector: 'app-cursos-maestro',
  imports: [PaginacionLista],
  templateUrl: './cursosMaestro.html',
})
export class CursosMaestro implements OnInit {
  cursos: Curso[] = [];
  asignaciones: AsignacionMaestroResponse[] = [];
  cargando = false;
  error = '';
  pagina = 0;
  totalPaginas = 0;
  totalAsignaciones = 0;
  private idMaestro = 0;

  constructor(
    private readonly cursoService: CursoService,
    private readonly asignacionService: AsignacionMaestrosSeccionService,
    private readonly inicioSesionService: InicioSesionService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const idMaestro = this.inicioSesionService.getSesion()?.idUsuario;
    if (!idMaestro) {
      this.error = 'No se encontró el maestro en la sesión.';
      return;
    }
    this.idMaestro = idMaestro;
    this.cargando = true;
    this.cursoService.obtenerCursosDeMaestro(idMaestro).subscribe({
      next: (cursos) => {
        this.cursos = cursos;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.error = error.status === 0
          ? 'No se pudo conectar con el backend.'
          : 'No se pudieron cargar los cursos asignados.';
        this.changeDetector.markForCheck();
      },
    });
    this.cargarAsignaciones();
  }

  cargarAsignaciones(): void {
    this.cargando = true;
    this.asignacionService.obtenerAsignacionesMaestroPaginadas(this.idMaestro, this.pagina).subscribe({
      next: (respuesta) => {
        this.asignaciones = respuesta.contenido;
        this.totalPaginas = respuesta.totalPaginas;
        this.totalAsignaciones = respuesta.totalElementos;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.error = error.status === 0
          ? 'No se pudo conectar con el backend.'
          : 'No se pudieron cargar las asignaciones del maestro.';
        this.changeDetector.markForCheck();
      },
    });
  }

  cambiarPagina(pagina: number): void {
    this.pagina = pagina;
    this.cargarAsignaciones();
  }

  nombreCurso(idCurso: number): string {
    return this.cursos.find((curso) => curso.idCurso === idCurso)?.nombreCurso ?? `Curso ${idCurso}`;
  }
}
