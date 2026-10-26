import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ConfiguracionColegio } from '../../../Objetos/objetosResponse/ConfiguracionColegio';
import { ConfiguracionSistemaService } from '../../accesorios/servicios/configuracion-sistema.service';

@Component({
  selector: 'app-panel-configuracion-sistema',
  imports: [FormsModule],
  templateUrl: './panelConfiguracionSistema.html',
})
export class PanelConfiguracionSistema implements OnInit {
  aniosLectivos: ConfiguracionColegio[] = [];
  nuevoAnio: number | null = null;
  cargando = false;
  guardandoAnio: number | null = null;
  creandoAnio = false;
  mensaje = '';
  error = '';

  constructor(
    private readonly configuracionService: ConfiguracionSistemaService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.cargarAniosLectivos();
  }

  cargarAniosLectivos(): void {
    this.cargando = true;
    this.error = '';

    this.configuracionService.obtenerAniosLectivos().subscribe({
      next: (anios) => {
        this.aniosLectivos = [...anios].sort(
          (primero, segundo) => segundo.anioLectivo - primero.anioLectivo,
        );
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.error = this.mensajeError(error, 'No se pudieron cargar los años lectivos.');
        this.changeDetector.markForCheck();
      },
    });
  }

  cambiarAnioLectivo(configuracion: ConfiguracionColegio): void {
    if (this.estaActivo(configuracion.estado) || this.guardandoAnio !== null) {
      return;
    }

    const confirmado = window.confirm(
      `¿Deseas establecer ${configuracion.anioLectivo} como el año lectivo activo?`,
    );
    if (!confirmado) {
      return;
    }

    this.error = '';
    this.mensaje = '';
    this.guardandoAnio = configuracion.anioLectivo;

    this.configuracionService.cambiarAnioLectivo(configuracion.anioLectivo).subscribe({
      next: (respuesta) => {
        this.mensaje =
          respuesta?.mensaje ??
          `El año lectivo ${configuracion.anioLectivo} se actualizó correctamente.`;
        this.guardandoAnio = null;
        this.cargarAniosLectivos();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardandoAnio = null;
        this.error = this.mensajeError(error, 'No se pudo cambiar el año lectivo.');
        this.changeDetector.markForCheck();
      },
    });
  }

  crearAnioLectivo(formulario: NgForm): void {
    this.error = '';
    this.mensaje = '';

    if (formulario.invalid || this.nuevoAnio === null || !Number.isInteger(this.nuevoAnio)) {
      formulario.control.markAllAsTouched();
      this.error = 'Escribe un año lectivo válido usando un número entero.';
      this.changeDetector.markForCheck();
      return;
    }

    if (this.aniosLectivos.some((configuracion) => configuracion.anioLectivo === this.nuevoAnio)) {
      this.error = `El año lectivo ${this.nuevoAnio} ya está registrado.`;
      this.changeDetector.markForCheck();
      return;
    }

    const anio = this.nuevoAnio;
    this.creandoAnio = true;

    this.configuracionService.crearAnioLectivo(anio).subscribe({
      next: (respuesta) => {
        this.mensaje = respuesta?.mensaje ?? `El año lectivo ${anio} se agregó correctamente.`;
        this.nuevoAnio = null;
        formulario.resetForm();
        this.creandoAnio = false;
        this.cargarAniosLectivos();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.creandoAnio = false;
        this.error = this.mensajeError(error, 'No se pudo agregar el año lectivo.');
        this.changeDetector.markForCheck();
      },
    });
  }

  estaActivo(estado: string): boolean {
    return estado?.toLocaleLowerCase() === 'activo' || estado?.toUpperCase() === 'ACTIVO';
  }

  private mensajeError(error: HttpErrorResponse, alternativo: string): string {
    if (error.status === 0) {
      return 'No se pudo conectar con el backend. Verifica que esté iniciado y que el proxy de Angular esté activo.';
    }

    let respuesta: unknown = error.error;
    if (typeof respuesta === 'string') {
      const textoRespuesta = respuesta;
      try {
        respuesta = JSON.parse(textoRespuesta) as unknown;
      } catch {
        if (textoRespuesta.trim()) {
          return textoRespuesta.trim();
        }
      }
    }

    if (respuesta && typeof respuesta === 'object') {
      const detalle = respuesta as { error?: unknown; mensaje?: unknown };
      if (typeof detalle.error === 'string') {
        return detalle.error;
      }
      if (typeof detalle.mensaje === 'string') {
        return detalle.mensaje;
      }
    }

    return `${alternativo} (HTTP ${error.status}).`;
  }
}
