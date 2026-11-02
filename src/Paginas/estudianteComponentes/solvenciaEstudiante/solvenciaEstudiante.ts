import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Solvencia } from '../../../Objetos/objetosResponse/Solvencia';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';
import { PagoService } from '../../accesorios/servicios/pago.service';

@Component({
  selector: 'app-solvencia-estudiante',
  imports: [FormsModule],
  templateUrl: './solvenciaEstudiante.html',
})
export class SolvenciaEstudiante {
  readonly meses = [
    { valor: 1, nombre: 'Enero' }, { valor: 2, nombre: 'Febrero' },
    { valor: 3, nombre: 'Marzo' }, { valor: 4, nombre: 'Abril' },
    { valor: 5, nombre: 'Mayo' }, { valor: 6, nombre: 'Junio' },
    { valor: 7, nombre: 'Julio' }, { valor: 8, nombre: 'Agosto' },
    { valor: 9, nombre: 'Septiembre' }, { valor: 10, nombre: 'Octubre' },
    { valor: 11, nombre: 'Noviembre' }, { valor: 12, nombre: 'Diciembre' },
  ];
  anio: string;
  mes = String(new Date().getMonth() + 1);
  solvencia: Solvencia | null = null;
  cargando = false;
  error = '';

  constructor(
    private readonly inicioSesionService: InicioSesionService,
    private readonly pagoService: PagoService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {
    this.anio = String(
      this.inicioSesionService.getSesion()?.anioLectivo ?? new Date().getFullYear(),
    );
  }

  consultar(formulario: NgForm): void {
    const idEstudiante = this.inicioSesionService.getSesion()?.idUsuario;
    if (formulario.invalid || !idEstudiante || this.cargando) {
      return;
    }
    this.error = '';
    this.solvencia = null;
    this.cargando = true;
    this.pagoService.obtenerSolvencia(idEstudiante, Number(this.anio), Number(this.mes)).subscribe({
      next: (solvencia) => {
        this.solvencia = solvencia;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.error = error.status === 0
          ? 'No se pudo conectar con el backend.'
          : 'No se pudo consultar la solvencia.';
        this.changeDetector.markForCheck();
      },
    });
  }
}
