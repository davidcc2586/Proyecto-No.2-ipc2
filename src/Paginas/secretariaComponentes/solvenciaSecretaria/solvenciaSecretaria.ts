import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ConfiguracionColegio } from '../../../Objetos/objetosResponse/ConfiguracionColegio';
import { EstudianteSelectResponse } from '../../../Objetos/objetosResponse/EstudianteSelectResponse';
import { Solvencia } from '../../../Objetos/objetosResponse/Solvencia';
import { ConfiguracionSistemaService } from '../../accesorios/servicios/configuracion-sistema.service';
import { EstudianteService } from '../../accesorios/servicios/estudiante.service';
import { PagoService } from '../../accesorios/servicios/pago.service';

@Component({
  selector: 'app-solvencia-secretaria',
  imports: [FormsModule],
  templateUrl: './solvenciaSecretaria.html',
})
export class SolvenciaSecretaria implements OnInit {
  readonly meses = [
    { valor: 1, nombre: 'Enero' },
    { valor: 2, nombre: 'Febrero' },
    { valor: 3, nombre: 'Marzo' },
    { valor: 4, nombre: 'Abril' },
    { valor: 5, nombre: 'Mayo' },
    { valor: 6, nombre: 'Junio' },
    { valor: 7, nombre: 'Julio' },
    { valor: 8, nombre: 'Agosto' },
    { valor: 9, nombre: 'Septiembre' },
    { valor: 10, nombre: 'Octubre' },
    { valor: 11, nombre: 'Noviembre' },
    { valor: 12, nombre: 'Diciembre' },
  ];

  estudiantes: EstudianteSelectResponse[] = [];
  aniosLectivos: ConfiguracionColegio[] = [];
  idEstudiante = '';
  anio = '';
  mes = '';
  solvencia: Solvencia | null = null;
  cargandoDatos = false;
  consultando = false;
  error = '';

  constructor(
    private readonly estudianteService: EstudianteService,
    private readonly configuracionService: ConfiguracionSistemaService,
    private readonly pagoService: PagoService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.cargarOpciones();
  }

  cargarOpciones(): void {
    this.cargandoDatos = true;
    this.error = '';
    let respuestas = 0;
    const finalizarCarga = (): void => {
      respuestas += 1;
      if (respuestas === 2) {
        this.cargandoDatos = false;
        this.changeDetector.markForCheck();
      }
    };

    this.estudianteService.obtenerEstudiantesActivos().subscribe({
      next: (estudiantes) => {
        this.estudiantes = estudiantes;
        finalizarCarga();
      },
      error: (error: HttpErrorResponse) => {
        this.error = this.mensajeError(error, 'No se pudieron cargar los estudiantes activos.');
        finalizarCarga();
      },
    });

    this.configuracionService.obtenerAniosLectivos().subscribe({
      next: (anios) => {
        this.aniosLectivos = [...anios].sort(
          (primero, segundo) => segundo.anioLectivo - primero.anioLectivo,
        );
        const activo = this.aniosLectivos.find(
          (configuracion) => String(configuracion.estado).toLocaleLowerCase() === 'activo',
        );
        if (activo) {
          this.anio = String(activo.anioLectivo);
        }
        finalizarCarga();
      },
      error: (error: HttpErrorResponse) => {
        this.error = this.mensajeError(error, 'No se pudieron cargar los años lectivos.');
        finalizarCarga();
      },
    });
  }

  consultarSolvencia(formulario: NgForm): void {
    this.error = '';
    this.solvencia = null;
    if (formulario.invalid) {
      formulario.control.markAllAsTouched();
      this.error = 'Selecciona un estudiante, año lectivo y mes.';
      return;
    }

    this.consultando = true;
    this.pagoService
      .obtenerSolvencia(Number(this.idEstudiante), Number(this.anio), Number(this.mes))
      .subscribe({
        next: (solvencia) => {
          this.solvencia = solvencia;
          this.consultando = false;
          this.changeDetector.markForCheck();
        },
        error: (error: HttpErrorResponse) => {
          this.consultando = false;
          this.error = this.mensajeError(error, 'No se pudo generar la solvencia económica.');
          this.changeDetector.markForCheck();
        },
      });
  }

  nombreMes(mes: number): string {
    return this.meses.find((opcion) => opcion.valor === mes)?.nombre ?? String(mes);
  }

  imprimir(): void {
    window.print();
  }

  private mensajeError(error: HttpErrorResponse, alternativo: string): string {
    if (error.status === 0) {
      return 'No se pudo conectar con el backend. Verifica que esté iniciado y que el proxy esté activo.';
    }

    let respuesta: unknown = error.error;
    if (typeof respuesta === 'string') {
      const texto = respuesta;
      try {
        respuesta = JSON.parse(texto) as unknown;
      } catch {
        if (texto.trim()) return texto.trim();
      }
    }
    if (respuesta && typeof respuesta === 'object') {
      const detalle = respuesta as { error?: unknown; mensaje?: unknown };
      if (typeof detalle.error === 'string') return detalle.error;
      if (typeof detalle.mensaje === 'string') return detalle.mensaje;
    }
    return `${alternativo} (HTTP ${error.status}).`;
  }
}
