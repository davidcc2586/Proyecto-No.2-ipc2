import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { forkJoin, Observable } from 'rxjs';
import { EstudianteSelectResponse } from '../../../Objetos/objetosResponse/EstudianteSelectResponse';
import { EstudianteService } from '../servicios/estudiante.service';
import { InicioSesionService } from '../servicios/inicio-sesion.service';
import { ReportesService } from '../servicios/reportes.service';

@Component({
  selector: 'app-reportes',
  imports: [FormsModule],
  templateUrl: './reportes.html',
})
export class Reportes implements OnInit {
  readonly estudiantes: EstudianteSelectResponse[] = [];
  puedeVerPagos = false;
  puedeVerIngresos = false;
  puedeVerMorosidad = false;
  idEstudiante = '';
  fechaInicio = '';
  fechaFin = '';
  generando = false;
  mensaje = '';
  error = '';

  constructor(
    private readonly reportesService: ReportesService,
    private readonly estudianteService: EstudianteService,
    private readonly sesionService: InicioSesionService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const rutaInicio = this.sesionService.rutaInicio(
      this.sesionService.getSesion()?.rolUsuario ?? '',
    );
    this.puedeVerPagos = rutaInicio === '/inicioAdmin' || rutaInicio === '/inicioSuperAdmin';
    this.puedeVerIngresos = rutaInicio === '/inicioSuperAdmin';
    this.puedeVerMorosidad =
      this.puedeVerPagos || rutaInicio === '/inicioBibliotecario';

    if (this.puedeVerPagos) {
      forkJoin([
        this.estudianteService.obtenerEstudiantesPorEstado('ACTIVO'),
        this.estudianteService.obtenerEstudiantesPorEstado('INACTIVO'),
        this.estudianteService.obtenerEstudiantesPorEstado('GRADUADO'),
      ]).subscribe({
        next: (estudiantesPorEstado) => {
          this.estudiantes.push(...estudiantesPorEstado.flat());
          this.changeDetector.markForCheck();
        },
        error: (error: HttpErrorResponse) =>
          this.mostrarError(error, 'No se pudieron cargar los estudiantes.'),
      });
    }
  }

  generarHistorial(): void {
    if (!this.validarFechas() || !this.idEstudiante) {
      if (!this.idEstudiante) {
        this.error = 'Selecciona un estudiante para generar el historial.';
      }
      return;
    }
    this.descargar(
      this.reportesService.historialPagos(
        Number(this.idEstudiante),
        this.fechaInicio,
        this.fechaFin,
      ),
      'historial-pagos.pdf',
      'No se pudo generar el historial de pagos.',
    );
  }

  generarIngresos(): void {
    if (!this.validarFechas()) {
      return;
    }
    this.descargar(
      this.reportesService.ingresos(this.fechaInicio, this.fechaFin),
      'reporte-ingresos.pdf',
      'No se pudo generar el reporte de ingresos.',
    );
  }

  generarMorosidad(): void {
    this.descargar(
      this.reportesService.morosidadBiblioteca(),
      'morosidad-biblioteca.pdf',
      'No se pudo generar el reporte de morosidad.',
    );
  }

  private validarFechas(): boolean {
    this.error = '';
    this.mensaje = '';
    if (this.fechaInicio && this.fechaFin && this.fechaInicio > this.fechaFin) {
      this.error = 'La fecha inicial no puede ser posterior a la fecha final.';
      return false;
    }
    return true;
  }

  private descargar(reporte: Observable<Blob>, nombreArchivo: string, mensajeError: string): void {
    if (this.generando) {
      return;
    }
    this.generando = true;
    this.error = '';
    this.mensaje = '';
    reporte.subscribe({
      next: (contenido) => {
        const url = URL.createObjectURL(contenido);
        const enlace = document.createElement('a');
        enlace.href = url;
        enlace.download = nombreArchivo;
        document.body.appendChild(enlace);
        enlace.click();
        enlace.remove();
        setTimeout(() => URL.revokeObjectURL(url), 1000);
        this.generando = false;
        this.mensaje = 'El reporte PDF se descargó correctamente.';
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.generando = false;
        this.mostrarError(error, mensajeError);
      },
    });
  }

  private mostrarError(respuesta: HttpErrorResponse, mensajePredeterminado: string): void {
    if (respuesta.error instanceof Blob) {
      void respuesta.error.text().then(
        (texto) => {
          try {
            const detalle: unknown = JSON.parse(texto);
            this.error =
              typeof detalle === 'object' &&
              detalle !== null &&
              'error' in detalle &&
              typeof detalle.error === 'string'
                ? detalle.error
                : mensajePredeterminado;
          } catch {
            this.error = mensajePredeterminado;
          }
          this.changeDetector.markForCheck();
        },
        () => {
          this.error = mensajePredeterminado;
          this.changeDetector.markForCheck();
        },
      );
      return;
    }
    const detalle = respuesta.error;
    this.error =
      typeof detalle === 'object' &&
      detalle !== null &&
      'error' in detalle &&
      typeof detalle.error === 'string'
        ? detalle.error
        : mensajePredeterminado;
    this.changeDetector.markForCheck();
  }
}
