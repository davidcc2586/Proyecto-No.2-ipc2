import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ConfiguracionColegio } from '../../../Objetos/objetosResponse/ConfiguracionColegio';
import { EstudianteRequest } from '../../../Objetos/objetosRequest/EstudianteRequest';
import { ConfiguracionSistemaService } from '../../accesorios/servicios/configuracion-sistema.service';
import { EstudianteService } from '../../accesorios/servicios/estudiante.service';

@Component({
  selector: 'app-inscripcion-secretaria',
  imports: [FormsModule],
  templateUrl: './inscripcionSecretaria.html',
})
export class InscripcionSecretaria implements OnInit {
  readonly camposEstudiante = {
    cui: '',
    nombre: '',
    apellido: '',
    fechaNacimiento: '',
    usuario: '',
    clave: '',
    claveConfirmacion: '',
    tipoSangre: '',
    alergia: 'Ninguna',
    nombreEncargado: '',
    telefonoEncargado: '',
    correoEncargado: '',
  };

  anioLectivo = 0;
  cargandoAnio = false;
  guardando = false;
  mensaje = '';
  error = '';

  constructor(
    private readonly configuracionService: ConfiguracionSistemaService,
    private readonly estudianteService: EstudianteService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.cargandoAnio = true;
    this.configuracionService.obtenerAniosLectivos().subscribe({
      next: (anios) => {
        const anioActivo = anios.find(
          (anio: ConfiguracionColegio) => String(anio.estado).toUpperCase() === 'ACTIVO',
        );
        this.anioLectivo = anioActivo?.anioLectivo ?? 0;
        this.cargandoAnio = false;
        if (this.anioLectivo === 0) {
          this.error = 'No hay un año lectivo activo para registrar estudiantes.';
        }
        this.changeDetector.detectChanges();
      },
      error: (error: HttpErrorResponse) => {
        this.cargandoAnio = false;
        this.error = this.mensajeError(error, 'No se pudo consultar el año lectivo activo.');
        this.changeDetector.detectChanges();
      },
    });
  }

  registrarEstudiante(formulario: NgForm): void {
    this.error = '';
    this.mensaje = '';

    if (
      formulario.invalid ||
      this.cargandoAnio ||
      this.anioLectivo <= 0 ||
      this.camposEstudiante.clave !== this.camposEstudiante.claveConfirmacion
    ) {
      formulario.control.markAllAsTouched();
      this.error =
        this.camposEstudiante.clave !== this.camposEstudiante.claveConfirmacion
          ? 'La contraseña y su confirmación no coinciden.'
          : 'Completa todos los datos del estudiante.';
      return;
    }

    const estudiante = new EstudianteRequest(
      this.camposEstudiante.cui,
      this.camposEstudiante.nombre,
      this.camposEstudiante.apellido,
      this.camposEstudiante.fechaNacimiento,
      this.camposEstudiante.usuario,
      this.camposEstudiante.clave,
      this.camposEstudiante.claveConfirmacion,
      String(this.anioLectivo),
      this.camposEstudiante.tipoSangre,
      this.camposEstudiante.alergia,
      this.camposEstudiante.nombreEncargado,
      this.camposEstudiante.telefonoEncargado,
      this.camposEstudiante.correoEncargado,
    );

    this.guardando = true;
    this.estudianteService.crearCuentaEstudiante(estudiante).subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = 'El perfil del estudiante se creó correctamente. El pago de inscripción se registra por separado en Cobros.';
        this.limpiarFormulario(formulario);
        this.changeDetector.detectChanges();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.error = this.mensajeError(error, 'No se pudo crear el perfil del estudiante.');
        this.changeDetector.detectChanges();
      },
    });
  }

  private limpiarFormulario(formulario: NgForm): void {
    formulario.resetForm();
    Object.assign(this.camposEstudiante, {
      cui: '',
      nombre: '',
      apellido: '',
      fechaNacimiento: '',
      usuario: '',
      clave: '',
      claveConfirmacion: '',
      tipoSangre: '',
      alergia: 'Ninguna',
      nombreEncargado: '',
      telefonoEncargado: '',
      correoEncargado: '',
    });
  }

  private mensajeError(error: HttpErrorResponse, alternativo: string): string {
    if (error.status === 0) {
      return 'No se pudo conectar con el backend. Verifica que esté iniciado y que el proxy esté activo.';
    }
    const detalle = error.error;
    if (detalle && typeof detalle === 'object' && 'error' in detalle && typeof detalle.error === 'string') {
      return detalle.error;
    }
    return `${alternativo} (HTTP ${error.status}).`;
  }
}
