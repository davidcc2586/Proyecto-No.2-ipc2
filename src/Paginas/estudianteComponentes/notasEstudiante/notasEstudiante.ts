import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NotaHistorialResponse } from '../../../Objetos/objetosResponse/NotaHistorialResponse';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';
import { NotasService } from '../../accesorios/servicios/notas.service';

@Component({
  selector: 'app-notas-estudiante',
  imports: [FormsModule],
  templateUrl: './notasEstudiante.html',
})
export class NotasEstudiante implements OnInit {
  historial: NotaHistorialResponse[] = [];
  anioSeleccionado = '';
  cursoSeleccionado = '';
  cargando = false;
  error = '';

  constructor(
    private readonly inicioSesionService: InicioSesionService,
    private readonly notasService: NotasService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const idEstudiante = this.inicioSesionService.getSesion()?.idUsuario;
    if (!idEstudiante) {
      this.error = 'No se encontró el estudiante en la sesión.';
      return;
    }

    this.cargando = true;
    this.notasService.obtenerHistorialEstudiante(idEstudiante).subscribe({
      next: (historial) => {
        this.historial = historial;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.error = this.mensajeError(error, 'No se pudo cargar el historial de notas.');
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
    });
  }

  get anios(): number[] {
    return [...new Set(this.historial.map((nota) => nota.anioLectivo))].sort((a, b) => b - a);
  }

  get cursos(): Array<{ idCurso: number; nombreCurso: string }> {
    const cursos = new Map<number, string>();
    for (const nota of this.historial) cursos.set(nota.idCurso, nota.nombreCurso);
    return [...cursos].map(([idCurso, nombreCurso]) => ({ idCurso, nombreCurso }))
      .sort((a, b) => a.nombreCurso.localeCompare(b.nombreCurso));
  }

  get notasFiltradas(): NotaHistorialResponse[] {
    return this.historial.filter((nota) =>
      (!this.anioSeleccionado || nota.anioLectivo === Number(this.anioSeleccionado)) &&
      (!this.cursoSeleccionado || nota.idCurso === Number(this.cursoSeleccionado)),
    );
  }

  private mensajeError(error: HttpErrorResponse, alternativo: string): string {
    if (error.status === 0) {
      return 'No se pudo conectar con el backend.';
    }
    if (error.status === 404) {
      return 'El backend instalado no incluye el historial de notas. Despliega el WAR actualizado y reinicia Tomcat.';
    }
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
