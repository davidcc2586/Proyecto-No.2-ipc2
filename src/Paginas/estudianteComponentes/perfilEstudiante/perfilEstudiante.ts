import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { EstudianteDatosResponse } from '../../../Objetos/objetosResponse/EstudianteDatosResponse';
import { EstudianteService } from '../../accesorios/servicios/estudiante.service';
import { InicioSesionService } from '../../accesorios/servicios/inicio-sesion.service';

@Component({
  selector: 'app-perfil-estudiante',
  templateUrl: './perfilEstudiante.html',
})
export class PerfilEstudiante implements OnInit {
  estudiante: EstudianteDatosResponse | null = null;
  cargando = false;
  error = '';

  constructor(
    private readonly inicioSesionService: InicioSesionService,
    private readonly estudianteService: EstudianteService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const idEstudiante = this.inicioSesionService.getSesion()?.idUsuario;
    if (!idEstudiante) {
      this.error = 'No se encontró el estudiante en la sesión.';
      return;
    }

    this.cargando = true;
    this.estudianteService.obtenerDatosEstudiante(idEstudiante).subscribe({
      next: (estudiante) => {
        this.estudiante = estudiante;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.error = error.status === 0
          ? 'No se pudo conectar con el backend.'
          : 'No se pudo consultar el perfil del estudiante.';
        this.changeDetector.markForCheck();
      },
    });
  }
}
