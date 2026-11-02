import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CarreraActualizarRequest } from '../../../Objetos/objetosResponse/CarreraActualizarRequest';
import { Carrera } from '../../../Objetos/objetosResponse/Carrera';
import { CarreraService } from '../../accesorios/servicios/carrera.service';
import { PaginacionLista } from '../../accesorios/paginacionLista/paginacionLista';

@Component({
  selector: 'app-gestion-carreras-admin',
  imports: [FormsModule, PaginacionLista],
  templateUrl: './gestionCarrerasAdmin.html',
})
export class GestionCarrerasAdmin implements OnInit {
  carreras: Carrera[] = [];
  nombreCarrera = '';
  carreraSeleccionada: Carrera | null = null;
  cargando = false;
  guardando = false;
  mensaje = '';
  error = '';
  pagina = 0;
  totalPaginas = 0;
  totalCarreras = 0;

  constructor(
    private readonly carreraService: CarreraService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.cargarCarreras();
  }

  cargarCarreras(): void {
    this.cargando = true;
    this.carreraService.obtenerCarrerasPaginadas(this.pagina).subscribe({
      next: (respuesta) => {
        this.carreras = respuesta.contenido;
        this.totalPaginas = respuesta.totalPaginas;
        this.totalCarreras = respuesta.totalElementos;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.mostrarError(error, 'No se pudieron cargar las carreras.');
      },
    });
  }

  cambiarPagina(pagina: number): void {
    this.pagina = pagina;
    this.cargarCarreras();
  }

  guardarCarrera(formulario: NgForm): void {
    if (formulario.invalid || this.guardando) {
      return;
    }
    this.guardando = true;
    const solicitud = this.carreraSeleccionada
      ? this.carreraService.actualizarCarrera(
          new CarreraActualizarRequest(String(this.carreraSeleccionada.idCarrera), this.nombreCarrera),
        )
      : this.carreraService.crearCarrera(this.nombreCarrera);

    solicitud.subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = this.carreraSeleccionada
          ? 'La carrera se modificó correctamente.'
          : 'La carrera se registró correctamente.';
        this.pagina = 0;
        this.cancelarEdicion();
        formulario.resetForm();
        this.cargarCarreras();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.mostrarError(error, 'No se pudo guardar la carrera.');
      },
    });
  }

  seleccionarCarrera(carrera: Carrera): void {
    this.carreraSeleccionada = carrera;
    this.nombreCarrera = carrera.nombreCarrera;
    this.mensaje = '';
  }

  cambiarEstado(carrera: Carrera): void {
    this.carreraService.cambiarEstadoCarrera(carrera.idCarrera).subscribe({
      next: () => {
        this.mensaje = 'El estado de la carrera se cambió correctamente.';
        this.pagina = 0;
        this.cargarCarreras();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudo cambiar el estado de la carrera.'),
    });
  }

  cancelarEdicion(): void {
    this.carreraSeleccionada = null;
    this.nombreCarrera = '';
  }

  private mostrarError(respuesta: HttpErrorResponse, alternativo: string): void {
    const detalle = respuesta.error;
    this.error =
      typeof detalle === 'object' && detalle !== null && 'error' in detalle &&
      typeof detalle.error === 'string'
        ? detalle.error
        : alternativo;
    this.changeDetector.markForCheck();
  }
}
