import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { EstadoCuentaResponse } from '../../../Objetos/objetosResponse/EstadoCuentaResponse';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';
import { PagoService } from '../../accesorios/servicios/pago.service';

@Component({
  selector: 'app-estado-cuenta-estudiante',
  imports: [CommonModule],
  templateUrl: './estadoCuentaEstudiante.html',
})
export class EstadoCuentaEstudiante implements OnInit {
  estadoCuenta: EstadoCuentaResponse | null = null;
  cargando = false;
  error = '';

  private readonly nombresMeses = [
    'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
    'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre',
  ];

  constructor(
    private readonly inicioSesionService: InicioSesionService,
    private readonly pagoService: PagoService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const idEstudiante = this.inicioSesionService.getSesion()?.idUsuario;
    if (!idEstudiante) {
      this.error = 'No se encontró el estudiante en la sesión.';
      return;
    }

    this.cargando = true;
    this.pagoService.obtenerEstadoCuenta(idEstudiante).subscribe({
      next: (estadoCuenta) => {
        this.estadoCuenta = estadoCuenta;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.error = this.mensajeError(error, 'No se pudo consultar el estado de cuenta.');
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
    });
  }

  nombreMes(mes: number): string {
    return this.nombresMeses[mes - 1] ?? String(mes);
  }

  get totalPagado(): number {
    return this.estadoCuenta?.pagos.reduce((total, pago) => total + pago.cantidad, 0) ?? 0;
  }

  get mesesPendientesTexto(): string {
    return this.estadoCuenta?.mesesPendientes.map((mes) => this.nombreMes(mes)).join(', ') ?? '';
  }

  formatoFecha(fecha: unknown): string {
    if (Array.isArray(fecha) && fecha.length >= 3) {
      return `${fecha[0]}-${String(fecha[1]).padStart(2, '0')}-${String(fecha[2]).padStart(2, '0')}`;
    }
    return typeof fecha === 'string' ? fecha : '—';
  }

  private mensajeError(error: HttpErrorResponse, alternativo: string): string {
    if (error.status === 0) return 'No se pudo conectar con el backend.';
    if (error.status === 404) {
      return 'El backend instalado no incluye el estado de cuenta. Despliega el WAR actualizado y reinicia Tomcat.';
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
