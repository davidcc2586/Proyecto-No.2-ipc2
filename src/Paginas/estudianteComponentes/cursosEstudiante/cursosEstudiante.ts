import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Curso } from '../../../Objetos/objetosResponse/Curso';
import { CursoService } from '../../accesorios/servicios/curso.service';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';
import { PaginacionLista } from '../../accesorios/paginacionLista/paginacionLista';

@Component({
  selector: 'app-cursos-estudiante',
  imports: [PaginacionLista],
  templateUrl: './cursosEstudiante.html',
})
export class CursosEstudiante implements OnInit {
  cursos: Curso[] = [];
  cargando = false;
  error = '';
  pagina = 0;
  totalPaginas = 0;
  totalCursos = 0;
  private idEstudiante = 0;

  constructor(
    private readonly cursoService: CursoService,
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
    this.cargarCursos();
  }

  cargarCursos(): void {
    this.cargando = true;
    this.cursoService.obtenerCursosDeEstudiantePaginados(this.idEstudiante, this.pagina).subscribe({
      next: (respuesta) => {
        this.cursos = respuesta.contenido;
        this.totalPaginas = respuesta.totalPaginas;
        this.totalCursos = respuesta.totalElementos;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.error = error.status === 0
          ? 'No se pudo conectar con el backend.'
          : 'No se pudieron cargar tus cursos.';
        this.changeDetector.markForCheck();
      },
    });
  }

  cambiarPagina(pagina: number): void {
    this.pagina = pagina;
    this.cargarCursos();
  }
}
