import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { GradoActualizarRequest } from '../../../Objetos/objetosRequest/GradoActualizarRequest';
import { GradoRequest } from '../../../Objetos/objetosRequest/GradoRequest';
import { SeccionActualizarRequest } from '../../../Objetos/objetosRequest/SeccionActualizarRequest';
import { SeccionRequest } from '../../../Objetos/objetosRequest/SeccionRequest';
import { Carrera } from '../../../Objetos/objetosResponse/Carrera';
import { ConfiguracionColegio } from '../../../Objetos/objetosResponse/ConfiguracionColegio';
import { Grado } from '../../../Objetos/objetosResponse/Grado';
import { Seccion } from '../../../Objetos/objetosResponse/Seccion';
import { SelectGradosResponse } from '../../../Objetos/objetosResponse/SelectGradosResponse';
import { CarreraService } from '../../accesorios/servicios/carrera.service';
import { ConfiguracionSistemaService } from '../../accesorios/servicios/configuracion-sistema.service';
import { GradoService } from '../../accesorios/servicios/grado.service';
import { SeccionService } from '../../accesorios/servicios/seccion.service';
import { PaginacionLista } from '../../accesorios/paginacionLista/paginacionLista';

@Component({
  selector: 'app-estructura-academica-admin',
  imports: [FormsModule, PaginacionLista],
  templateUrl: './estructuraAcademicaAdmin.html',
})
export class EstructuraAcademicaAdmin implements OnInit {
  readonly tiposGrado = [
    { key: 'PRE_PRIMARIA', nombre: 'Pre-Primaria' },
    { key: 'PRIMARIA', nombre: 'Primaria' },
    { key: 'BASICO', nombre: 'Básico' },
    { key: 'DIVERSIFICADO', nombre: 'Diversificado' },
  ];
  readonly letrasSeccion = 'ABCDEFGHIJK'.split('');
  grados: Grado[] = [];
  gradosSelect: SelectGradosResponse[] = [];
  carreras: Carrera[] = [];
  anios: ConfiguracionColegio[] = [];
  secciones: Seccion[] = [];
  gradoForm = { nombreGrado: '', gradoColegio: '', idCarrera: '' };
  seccionForm = { idGrado: '', anio: '', nombreSeccion: '' };
  gradoSeleccionado: Grado | null = null;
  seccionSeleccionada: Seccion | null = null;
  cargando = false;
  guardando = false;
  mensaje = '';
  error = '';
  paginaGrados = 0;
  totalPaginasGrados = 0;
  totalGrados = 0;
  paginaSecciones = 0;
  totalPaginasSecciones = 0;
  totalSecciones = 0;

  constructor(
    private readonly gradoService: GradoService,
    private readonly seccionService: SeccionService,
    private readonly carreraService: CarreraService,
    private readonly configuracionService: ConfiguracionSistemaService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.cargarGrados();
    this.gradoService.obtenerGradosSelect().subscribe({
      next: (grados) => {
        this.gradosSelect = grados;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar los grados.'),
    });
    this.carreraService.obtenerCarreras().subscribe({
      next: (carreras) => {
        this.carreras = carreras;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar las carreras.'),
    });
    this.configuracionService.obtenerAniosLectivos().subscribe({
      next: (anios) => {
        this.anios = anios;
        const activo = anios.find((anio) => String(anio.estado).toUpperCase() === 'ACTIVO');
        this.seccionForm.anio = String(activo?.anioLectivo ?? '');
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar los años lectivos.'),
    });
  }

  cargarGrados(): void {
    this.cargando = true;
    this.gradoService.obtenerGradosPaginados(this.paginaGrados).subscribe({
      next: (respuesta) => {
        this.grados = respuesta.contenido;
        this.totalPaginasGrados = respuesta.totalPaginas;
        this.totalGrados = respuesta.totalElementos;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.mostrarError(error, 'No se pudieron cargar los grados.');
      },
    });
  }

  cambiarPaginaGrados(pagina: number): void {
    this.paginaGrados = pagina;
    this.cargarGrados();
  }

  guardarGrado(formulario: NgForm): void {
    if (formulario.invalid || this.guardando) {
      return;
    }
    this.guardando = true;
    const solicitud = this.gradoSeleccionado
      ? this.gradoService.actualizarGrado(
          new GradoActualizarRequest(
            this.gradoForm.nombreGrado,
            this.gradoForm.gradoColegio,
            this.gradoForm.idCarrera,
            String(this.gradoSeleccionado.idGrado),
          ),
        )
      : this.gradoService.crearGrado(
          new GradoRequest(
            this.gradoForm.nombreGrado,
            this.gradoForm.gradoColegio,
            this.gradoForm.idCarrera,
          ),
        );
    solicitud.subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = this.gradoSeleccionado ? 'El grado se modificó correctamente.' : 'El grado se registró correctamente.';
        this.cancelarGrado();
        formulario.resetForm();
        this.cargarGrados();
        this.gradoService.obtenerGradosSelect().subscribe({
          next: (grados) => {
            this.gradosSelect = grados;
            this.changeDetector.markForCheck();
          },
          error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron actualizar los grados.'),
        });
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.mostrarError(error, 'No se pudo guardar el grado.');
      },
    });
  }

  seleccionarGrado(grado: Grado): void {
    this.gradoSeleccionado = grado;
    this.gradoForm = {
      nombreGrado: grado.nombreGrado ?? '',
      gradoColegio: this.tiposGrado.find((tipo) => tipo.nombre === grado.gradoColegio || tipo.key === grado.gradoColegio)?.key ?? '',
      idCarrera: String(grado.idCarrera),
    };
  }

  cambiarTipoGrado(tipo: string): void {
    this.gradoForm.gradoColegio = tipo;
    if (tipo !== 'DIVERSIFICADO') {
      this.gradoForm.idCarrera = '0';
    } else if (this.gradoForm.idCarrera === '0') {
      this.gradoForm.idCarrera = '';
    }
  }

  cancelarGrado(): void {
    this.gradoSeleccionado = null;
    this.gradoForm = { nombreGrado: '', gradoColegio: '', idCarrera: '' };
  }

  cargarSecciones(): void {
    this.paginaSecciones = 0;
    this.cargarPaginaSecciones();
  }

  private cargarPaginaSecciones(): void {
    if (
      this.seccionSeleccionada &&
      this.seccionSeleccionada.idGrado !== Number(this.seccionForm.idGrado)
    ) {
      this.seccionSeleccionada = null;
    }
    this.secciones = [];
    if (!this.seccionForm.idGrado) {
      return;
    }
    this.seccionService.obtenerSeccionesDeGradoPaginadas(
      Number(this.seccionForm.idGrado),
      this.paginaSecciones,
    ).subscribe({
      next: (respuesta) => {
        this.secciones = respuesta.contenido;
        this.totalPaginasSecciones = respuesta.totalPaginas;
        this.totalSecciones = respuesta.totalElementos;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar las secciones.'),
    });
  }

  cambiarPaginaSecciones(pagina: number): void {
    this.paginaSecciones = pagina;
    this.cargarPaginaSecciones();
  }

  guardarSeccion(formulario: NgForm): void {
    if (formulario.invalid || this.guardando) {
      return;
    }
    this.guardando = true;
    const solicitud = this.seccionSeleccionada
      ? this.seccionService.actualizarSeccion(
          new SeccionActualizarRequest(
            String(this.seccionSeleccionada.idSeccion),
            this.seccionForm.idGrado,
            this.seccionForm.anio,
            this.seccionForm.nombreSeccion,
          ),
        )
      : this.seccionService.crearSeccion(
          new SeccionRequest(
            this.seccionForm.idGrado,
            this.seccionForm.anio,
            this.seccionForm.nombreSeccion,
          ),
        );
    solicitud.subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = this.seccionSeleccionada
          ? 'La sección se modificó correctamente.'
          : 'La sección se registró correctamente.';
        this.seccionSeleccionada = null;
        formulario.resetForm();
        this.seccionForm.idGrado = '';
        this.seccionForm.anio = String(this.anios.find((anio) => String(anio.estado).toUpperCase() === 'ACTIVO')?.anioLectivo ?? '');
        this.secciones = [];
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.mostrarError(error, 'No se pudo guardar la sección.');
      },
    });
  }

  seleccionarSeccion(seccion: Seccion): void {
    this.seccionSeleccionada = seccion;
    this.seccionForm = {
      idGrado: String(seccion.idGrado),
      anio: String(seccion.anio),
      nombreSeccion: seccion.seccion,
    };
    this.cargarSecciones();
  }

  cancelarSeccion(): void {
    this.seccionSeleccionada = null;
    this.seccionForm = { idGrado: '', anio: '', nombreSeccion: '' };
    this.secciones = [];
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
