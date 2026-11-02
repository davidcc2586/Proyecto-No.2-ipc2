import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { PagoRequest } from '../../../Objetos/objetosRequest/PagoRequest';
import { EstudianteSelectResponse } from '../../../Objetos/objetosResponse/EstudianteSelectResponse';
import { UsuarioSelectResponse } from '../../../Objetos/objetosResponse/UsuarioSelectResponse';
import { EstudianteService } from '../../accesorios/servicios/estudiante.service';
import { PagoService } from '../../accesorios/servicios/pago.service';
import { UsuarioService } from '../../accesorios/servicios/usuario.service';

@Component({
  selector: 'app-cobros-secretaria',
  imports: [FormsModule],
  templateUrl: './cobrosSecretaria.html',
})
export class CobrosSecretaria implements OnInit {
  readonly tiposPago = [
    { valor: 'INSCRIPCIÓN', nombre: 'Inscripción' },
    { valor: 'COLEGIATURA', nombre: 'Colegiatura' },
    { valor: 'ACTIVIDAD_EXTRACURRICULAR', nombre: 'Actividad extracurricular' },
    { valor: 'MULTA', nombre: 'Multa' },
  ];

  estudiantes: EstudianteSelectResponse[] = [];
  secretarios: UsuarioSelectResponse[] = [];
  idEstudiante = '';
  idSecretario = '';
  tipoPago = '';
  cantidad = '';
  descripcion = '';
  nombreConsumidor = 'CONSUMIDOR FINAL';
  nit = 'CF';
  fecha = this.fechaHoy();
  cargandoEstudiantes = false;
  cargandoSecretarios = false;
  guardando = false;
  mensaje = '';
  error = '';

  constructor(
    private readonly estudianteService: EstudianteService,
    private readonly pagoService: PagoService,
    private readonly usuarioService: UsuarioService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.cargandoEstudiantes = true;
    this.estudianteService.obtenerEstudiantesActivos().subscribe({
      next: (estudiantes) => {
        this.estudiantes = estudiantes;
        this.cargandoEstudiantes = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargandoEstudiantes = false;
        this.error = this.mensajeError(error, 'No se pudieron cargar los estudiantes activos.');
        this.changeDetector.markForCheck();
      },
    });

    this.cargandoSecretarios = true;
    this.usuarioService.obtenerSecretariosSelect().subscribe({
      next: (secretarios) => {
        this.secretarios = secretarios;
        this.cargandoSecretarios = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargandoSecretarios = false;
        this.error = this.mensajeError(error, 'No se pudieron cargar los secretarios.');
        this.changeDetector.markForCheck();
      },
    });
  }

  registrarPago(formulario: NgForm): void {
    this.error = '';
    this.mensaje = '';

    if (
      formulario.invalid ||
      !Number.isInteger(Number(this.idSecretario)) ||
      Number(this.idSecretario) <= 0
    ) {
      formulario.control.markAllAsTouched();
      this.error = 'Completa todos los datos del cobro y selecciona un secretario válido.';
      return;
    }

    this.guardando = true;
    const pago = new PagoRequest(
      this.idEstudiante,
      this.idSecretario,
      this.tipoPago,
      this.cantidad,
      this.descripcion,
      this.fecha,
      this.nombreConsumidor,
      this.nit,
    );

    this.pagoService.registrarPago(pago).subscribe({
      next: (respuesta) => {
        this.mensaje = `${respuesta?.mensaje ?? 'El pago se registró y su factura fue emitida.'} Número de factura: ${respuesta.idFactura}.`;
        this.guardando = false;
        const idSecretario = this.idSecretario;
        formulario.resetForm();
        this.idEstudiante = '';
        this.idSecretario = idSecretario;
        this.tipoPago = '';
        this.cantidad = '';
        this.descripcion = '';
        this.nombreConsumidor = 'CONSUMIDOR FINAL';
        this.nit = 'CF';
        this.fecha = this.fechaHoy();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.error = this.mensajeError(error, 'No se pudo registrar el pago.');
        this.changeDetector.markForCheck();
      },
    });
  }

  private fechaHoy(): string {
    const hoy = new Date();
    return `${hoy.getFullYear()}-${String(hoy.getMonth() + 1).padStart(2, '0')}-${String(hoy.getDate()).padStart(2, '0')}`;
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
