import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { AsignacionEstudianteRequest } from '../../../Objetos/objetosRequest/AsignacionEstudianteRequest';
import { EstudianteActualizarRequest } from '../../../Objetos/objetosRequest/EstudianteActualizarRequest';
import { EstadoEstudiante } from '../../../Objetos/enums/EstadoEstudiante';
import { Estudiante } from '../../../Objetos/objetosResponse/Estudiante';
import { EstudianteDatosResponse } from '../../../Objetos/objetosResponse/EstudianteDatosResponse';
import { EstudianteSelectResponse } from '../../../Objetos/objetosResponse/EstudianteSelectResponse';
import { PaginacionLista } from '../paginacionLista/paginacionLista';
import { Grado } from '../../../Objetos/objetosResponse/Grado';
import { Seccion } from '../../../Objetos/objetosResponse/Seccion';
import { AsignacionEstudiantesService } from '../servicios/asignacion-estudiantes.service';
import { EstudianteService } from '../servicios/estudiante.service';
import { GradoService } from '../servicios/grado.service';
import { InicioSesionService } from '../servicios/inicio-sesion.service';
import { SeccionService } from '../servicios/seccion.service';

@Component({
  selector: 'app-consulta-estudiantes',
  imports: [FormsModule, PaginacionLista],
  templateUrl: './consultaEstudiantes.html',
})
export class ConsultaEstudiantes implements OnInit {
  readonly estados = [
    { valor: 'ACTIVO', nombre: EstadoEstudiante.ACTIVO },
    { valor: 'INACTIVO', nombre: EstadoEstudiante.INACTIVO },
    { valor: 'GRADUADO', nombre: EstadoEstudiante.GRADUADO },
  ];
  idGradoExpediente = '';
  idSeccionExpediente = '';
  seccionesExpediente: Seccion[] = [];
  estudiantesDeSeccion: Estudiante[] = [];
  datosEstudianteSeleccionado: EstudianteDatosResponse | null = null;
  idEstudianteSeleccionado = 0;
  cargandoDatosEstudiante = false;
  editarDatosEstudiante = false;
  datosEncargado = {
    alergia: '',
    nombreEncargado: '',
    telefonoEncargado: '',
    correoEncargado: '',
  };

  esAdmin = false;
  puedeVerExpediente = false;
  estadoSeleccionado = 'ACTIVO';
  estudiantes: Estudiante[] = [];
  grados: Grado[] = [];
  secciones: Seccion[] = [];
  estudiantesActivos: EstudianteSelectResponse[] = [];
  estadoNuevoPorEstudiante: Record<number, string> = {};
  estudianteSeleccionado = '';
  idGradoSeleccionado = '';
  idSeccionSeleccionada = '';
  cargando = false;
  guardando = false;
  mensaje = '';
  error = '';
  paginaEstudiantes = 0;
  totalPaginasEstudiantes = 0;
  totalEstudiantes = 0;
  paginaExpedientes = 0;
  totalPaginasExpedientes = 0;
  totalExpedientes = 0;
  cargandoExpedientes = false;

  constructor(
    private readonly estudianteService: EstudianteService,
    private readonly gradoService: GradoService,
    private readonly seccionService: SeccionService,
    private readonly asignacionService: AsignacionEstudiantesService,
    private readonly sesionService: InicioSesionService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const rutaInicio = this.sesionService.rutaInicio(
      this.sesionService.getSesion()?.rolUsuario ?? '',
    );
    this.esAdmin = rutaInicio === '/inicioAdmin';
    this.puedeVerExpediente = this.esAdmin || rutaInicio === '/inicioSecretaria';
    this.cargarEstudiantes();

    if (this.esAdmin) {
      this.gradoService.obtenerGrados().subscribe({
        next: (grados) => {
          this.grados = grados;
          this.changeDetector.markForCheck();
        },
        error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar los grados.'),
      });
      this.estudianteService.obtenerEstudiantesActivos().subscribe({
        next: (estudiantes) => {
          this.estudiantesActivos = estudiantes;
          this.changeDetector.markForCheck();
        },
        error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar estudiantes activos.'),
      });
    }
  }

  cargarEstudiantes(): void {
    this.cargando = true;
    this.error = '';
    this.paginaEstudiantes = 0;
    this.cargarPaginaEstudiantes();
  }

  private cargarPaginaEstudiantes(): void {
    this.cargando = true;
    this.estudianteService.obtenerEstudiantesPorEstadoPaginados(this.estadoSeleccionado,this.paginaEstudiantes,).subscribe({
      next: (respuesta) => {
        this.estudiantes = respuesta.contenido;
        this.totalPaginasEstudiantes = respuesta.totalPaginas;
        this.totalEstudiantes = respuesta.totalElementos;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.mostrarError(error, 'No se pudieron cargar los estudiantes.');
      },
    });
  }

  cambiarPaginaEstudiantes(pagina: number): void {
    this.paginaEstudiantes = pagina;
    this.cargarPaginaEstudiantes();
  }

  cargarSecciones(): void {
    this.idSeccionSeleccionada = '';
    if (!this.idGradoSeleccionado) {
      this.secciones = [];
      return;
    }
    this.seccionService.obtenerSeccionesDeGrado(Number(this.idGradoSeleccionado)).subscribe({
      next: (secciones) => {
        this.secciones = secciones;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar las secciones.'),
    });
  }

  cargarSeccionesExpediente(): void {
    this.idSeccionExpediente = '';
    this.seccionesExpediente = [];
    this.estudiantesDeSeccion = [];
    if (!this.idGradoExpediente) {
      return;
    }
    this.seccionService.obtenerSeccionesDeGrado(Number(this.idGradoExpediente)).subscribe({
      next: (secciones) => {
        this.seccionesExpediente = secciones;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron cargar las secciones.'),
    });
  }

  cargarExpedientes(): void {
    this.estudiantesDeSeccion = [];
    if (!this.idSeccionExpediente) {
      return;
    }
    this.cargandoExpedientes = true;
    this.estudianteService.obtenerEstudiantesDeSeccionPaginados(
      Number(this.idSeccionExpediente),
      this.paginaExpedientes,
    ).subscribe({
      next: (respuesta) => {
        this.estudiantesDeSeccion = respuesta.contenido;
        this.totalPaginasExpedientes = respuesta.totalPaginas;
        this.totalExpedientes = respuesta.totalElementos;
        this.cargandoExpedientes = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargandoExpedientes = false;
        this.mostrarError(error, 'No se pudo consultar el expediente de la sección.');
      },
    });
  }

  cambiarPaginaExpedientes(pagina: number): void {
    this.paginaExpedientes = pagina;
    this.cargarExpedientes();
  }

  cambiarSeccionExpediente(): void {
    this.paginaExpedientes = 0;
    this.estudiantesDeSeccion = [];
  }

  consultarDatosEstudiante(estudiante: Estudiante): void {
    this.cargarDatosEstudiante(estudiante.idEstudiante);
  }

  private cargarDatosEstudiante(idEstudiante: number): void {
    this.idEstudianteSeleccionado = idEstudiante;
    this.datosEstudianteSeleccionado = null;
    this.cargandoDatosEstudiante = true;
    this.editarDatosEstudiante = false;
    this.changeDetector.detectChanges();
    this.estudianteService.obtenerDatosEstudiante(idEstudiante).subscribe({
      next: (datos) => {
        this.datosEstudianteSeleccionado = datos;
        this.cargandoDatosEstudiante = false;
        this.datosEncargado = {
          alergia: datos.alergia ?? '',
          nombreEncargado: datos.nombreEncargado ?? '',
          telefonoEncargado: datos.telefonoEncargado ?? '',
          correoEncargado: datos.correoEncargado ?? '',
        };
        this.changeDetector.detectChanges();
      },
      error: (error: HttpErrorResponse) => {
        this.cargandoDatosEstudiante = false;
        this.mostrarError(error, 'No se pudieron consultar los datos del estudiante.');
      },
    });
  }

  guardarDatosEstudiante(formulario: NgForm): void {
    if (formulario.invalid || !this.idEstudianteSeleccionado || this.guardando) {
      return;
    }
    this.guardando = true;
    this.estudianteService.actualizarEstudiante(
      new EstudianteActualizarRequest(
        this.idEstudianteSeleccionado,
        this.datosEncargado.alergia,
        this.datosEncargado.nombreEncargado,
        this.datosEncargado.telefonoEncargado,
        this.datosEncargado.correoEncargado,
      ),
    ).subscribe({
      next: () => {
        this.guardando = false;
        this.editarDatosEstudiante = false;
        this.mensaje = 'Los datos del estudiante se actualizaron correctamente.';
        this.cargarDatosEstudiante(this.idEstudianteSeleccionado);
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.mostrarError(error, 'No se pudieron actualizar los datos del estudiante.');
      },
    });
  }

  cambiarEstadoEstudiante(estudiante: Estudiante): void {
    const nuevoEstado = this.estadoNuevoPorEstudiante[estudiante.idEstudiante];
    if (!nuevoEstado) {
      return;
    }
    this.estudianteService.cambiarEstadoEstudiante(estudiante.idEstudiante, nuevoEstado).subscribe({
      next: () => {
        this.mensaje = `El estudiante ahora está ${nuevoEstado.toLocaleLowerCase()}.`;
        delete this.estadoNuevoPorEstudiante[estudiante.idEstudiante];
        this.cargarEstudiantes();
        this.estudianteService.obtenerEstudiantesActivos().subscribe({
          next: (estudiantes) => {
            this.estudiantesActivos = estudiantes;
            this.changeDetector.markForCheck();
          },
          error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudieron actualizar los estudiantes activos.'),
        });
        if (this.idEstudianteSeleccionado === estudiante.idEstudiante) {
          this.datosEstudianteSeleccionado = null;
          this.idEstudianteSeleccionado = 0;
        }
      },
      error: (error: HttpErrorResponse) => this.mostrarError(error, 'No se pudo cambiar el estado del estudiante.'),
    });
  }

  asignarEstudiante(formulario: NgForm): void {
    if (formulario.invalid || this.guardando) {
      return;
    }
    this.guardando = true;
    const asignacion = new AsignacionEstudianteRequest(
      this.idSeccionSeleccionada,
      this.estudianteSeleccionado,
    );
    this.asignacionService.asignarEstudiante(asignacion).subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = 'El estudiante se asignó a la sección correctamente.';
        formulario.resetForm();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.mostrarError(error, 'El estudiante se encuentra asignado.');
      },
    });
  }

  private mostrarError(respuesta: HttpErrorResponse, alternativo: string): void {
    const detalle = respuesta.error;
    const mensajeApi =
      typeof detalle === 'object' && detalle !== null && 'error' in detalle
        ? detalle.error
        : null;
    this.error =
      typeof mensajeApi === 'string'
        ? mensajeApi
        : respuesta.status === 0
          ? 'No se pudo conectar con el backend.'
          : alternativo;
    this.changeDetector.markForCheck();
  }
}
