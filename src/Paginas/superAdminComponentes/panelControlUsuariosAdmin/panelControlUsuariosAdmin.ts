import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { UsuarioActualizarRequest } from '../../../Objetos/objetosRequest/UsuarioActualizarRequest';
import { UsuarioRequest } from '../../../Objetos/objetosRequest/UsuarioRequest';
import { Usuario } from '../../../Objetos/objetosResponse/Usuario';
import { RolUsuario } from '../../../Objetos/enums/RolUsuario';
import { UsuarioService } from '../../accesorios/servicios/usuario.service';

type ModoFormulario = 'crear' | 'ver' | 'editar';

interface DatosUsuarioFormulario {
  cui: string;
  nombre: string;
  apellido: string;
  fechaNacimiento: string;
  usuario: string;
  clave: string;
  claveConfirmacion: string;
  rolUsuario: string;
  fechaContratacion: string;
  salario: string;
}

@Component({
  selector: 'app-panelControlUsuariosAdmin',
  imports: [FormsModule],
  templateUrl: './panelControlUsuariosAdmin.html',
})
export class PanelControlUsuariosAdmin implements OnInit {
  readonly roles = [
    { key: 'SUPERADMIN', label: RolUsuario.SUPERADMIN },
    { key: 'ADMIN', label: RolUsuario.ADMIN },
    { key: 'SECRETARIO', label: RolUsuario.SECRETARIO },
    { key: 'BIBLIOTECARIO', label: RolUsuario.BIBLIOTECARIO },
    { key: 'PERSONAL', label: RolUsuario.PERSONAL },
    { key: 'MAESTRO', label: RolUsuario.MAESTRO },
    { key: 'ESTUDIANTE', label: RolUsuario.ESTUDIANTE },
  ];

  usuarios: Usuario[] = [];
  usuarioSeleccionado: Usuario | null = null;
  selectedRole = 'ADMIN';
  busqueda = '';
  modo: ModoFormulario = 'crear';
  cargandoUsuarios = false;
  cargandoDetalle = false;
  guardando = false;
  mensaje = '';
  error = '';

  formulario: DatosUsuarioFormulario = this.formularioVacio();

  constructor(
    private readonly usuarioService: UsuarioService,
    private readonly changeDetector: ChangeDetectorRef,
  ){}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  get usuariosFiltrados(): Usuario[] {
    const termino = this.busqueda.trim().toLocaleLowerCase();
    if (!termino) {
      return this.usuarios;
    }

    return this.usuarios.filter((usuario) =>
      `${usuario.nombre} ${usuario.apellido} ${usuario.usuario} ${usuario.cui}`
        .toLocaleLowerCase()
        .includes(termino),
    );
  }

  cargarUsuarios(): void {
    this.cargandoUsuarios = true;
    this.error = '';

    this.usuarioService.obtenerUsuariosPorRol(this.selectedRole).subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios;
        this.cargandoUsuarios = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargandoUsuarios = false;
        this.error = this.mensajeError(error, 'No se pudieron cargar los usuarios.');
        this.changeDetector.markForCheck();
      },
    });
  }

  cambiarRol(): void {
    this.usuarioSeleccionado = null;
    this.modo = 'crear';
    this.formulario = this.formularioVacio();
    this.cargarUsuarios();
  }

  nuevoUsuario(): void {
    this.usuarioSeleccionado = null;
    this.modo = 'crear';
    this.formulario = this.formularioVacio();
    this.mensaje = '';
    this.error = '';
  }

  verUsuario(usuario: Usuario): void {
    this.cargandoDetalle = true;
    this.error = '';

    this.usuarioService.obtenerUsuario(usuario.idUsuario).subscribe({
      next: (detalle) => {
        this.usuarioSeleccionado = detalle;
        this.modo = 'ver';
        this.formulario = this.formularioDesdeUsuario(detalle);
        this.selectedRole = this.obtenerClaveRol(detalle.rolUsuario);
        this.cargandoDetalle = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargandoDetalle = false;
        this.error = this.mensajeError(error, 'No se pudieron consultar los datos del usuario.');
        this.changeDetector.markForCheck();
      },
    });
  }

  editarUsuario(): void {
    if (!this.usuarioSeleccionado) {
      return;
    }

    this.modo = 'editar';
    this.formulario.clave = '';
    this.formulario.claveConfirmacion = '';
    this.mensaje = '';
    this.error = '';
  }

  guardarUsuario(formulario: NgForm): void {
    this.error = '';
    this.mensaje = '';

    if (formulario.invalid) {
      formulario.control.markAllAsTouched();
      this.error = 'Completa todos los campos obligatorios antes de guardar.';
      this.changeDetector.markForCheck();
      return;
    }

    this.guardando = true;

    if (this.modo === 'crear') {
      const request = new UsuarioRequest(
        this.formulario.cui,
        this.formulario.nombre,
        this.formulario.apellido,
        this.formulario.fechaNacimiento,
        this.formulario.usuario,
        this.formulario.clave,
        this.formulario.claveConfirmacion,
        this.formulario.rolUsuario,
        this.formulario.fechaContratacion,
        this.formulario.salario,
      );

      this.usuarioService.crearUsuario(request).subscribe({
        next: () => {
          this.guardando = false;
          this.mensaje = 'La cuenta se creó correctamente.';
          this.selectedRole = request.rolUsuario;
          this.formulario = this.formularioVacio();
          this.cargarUsuarios();
          this.changeDetector.markForCheck();
        },
        error: (error: HttpErrorResponse) => {
          this.guardando = false;
          this.error = this.mensajeError(error, 'No se pudo crear la cuenta.');
          this.changeDetector.markForCheck();
        },
      });
      return;
    }

    if (!this.usuarioSeleccionado) {
      this.guardando = false;
      this.error = 'Selecciona un usuario antes de guardar los cambios.';
      return;
    }

    const request = new UsuarioActualizarRequest(
      String(this.usuarioSeleccionado.idUsuario),
      this.formulario.clave,
      this.formulario.claveConfirmacion,
      this.formulario.usuario,
      this.formulario.salario,
      this.formulario.rolUsuario,
    );

    this.usuarioService.actualizarUsuario(request).subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = 'Los cambios del usuario se guardaron correctamente.';
        this.verUsuario(this.usuarioSeleccionado!);
        this.cargarUsuarios();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.error = this.mensajeError(error, 'No se pudieron guardar los cambios.');
        this.changeDetector.markForCheck();
      },
    });
  }

  cambiarEstado(usuario: Usuario): void {
    const confirmado = window.confirm(
      `¿Deseas cambiar el estado de la cuenta de ${usuario.nombre} ${usuario.apellido}?`,
    );
    if (!confirmado) {
      return;
    }

    this.error = '';
    this.mensaje = '';
    this.usuarioService.cambiarEstado(usuario.idUsuario).subscribe({
      next: (respuesta) => {
        this.mensaje = respuesta.mensaje || 'El estado de la cuenta se actualizó correctamente.';
        this.cargarUsuarios();
        if (this.usuarioSeleccionado?.idUsuario === usuario.idUsuario) {
          this.verUsuario(usuario);
        }
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.error = this.mensajeError(error, 'No se pudo cambiar el estado de la cuenta.');
        this.changeDetector.markForCheck();
      },
    });
  }

  cancelarEdicion(): void {
    if (this.usuarioSeleccionado) {
      this.modo = 'ver';
      this.formulario = this.formularioDesdeUsuario(this.usuarioSeleccionado);
      return;
    }
    this.nuevoUsuario();
  }

  nombreRol(rol: RolUsuario | string): string {
    return this.roles.find((opcion) => opcion.key === rol || opcion.label === rol)?.label ?? rol;
  }

  private formularioVacio(): DatosUsuarioFormulario {
    return {
      cui: '',
      nombre: '',
      apellido: '',
      fechaNacimiento: '',
      usuario: '',
      clave: '',
      claveConfirmacion: '',
      rolUsuario: this.selectedRole,
      fechaContratacion: '',
      salario: '',
    };
  }

  private formularioDesdeUsuario(usuario: Usuario): DatosUsuarioFormulario {
    return {
      cui: usuario.cui ?? '',
      nombre: usuario.nombre ?? '',
      apellido: usuario.apellido ?? '',
      fechaNacimiento: usuario.fechaNacimiento ?? '',
      usuario: usuario.usuario ?? '',
      clave: '',
      claveConfirmacion: '',
      rolUsuario: this.obtenerClaveRol(usuario.rolUsuario),
      fechaContratacion: usuario.fechaContratacion ?? '',
      salario: usuario.salario === null || usuario.salario === undefined ? '' : String(usuario.salario),
    };
  }

  private obtenerClaveRol(rol: RolUsuario | string): string {
    return this.roles.find((opcion) => opcion.key === rol || opcion.label === rol)?.key ?? 'ADMIN';
  }

  private mensajeError(error: HttpErrorResponse, alternativo: string): string {
    if (error.status === 0) {
      return 'No se pudo conectar con el backend. Verifica que el servidor esté iniciado y que permita solicitudes desde frontend.';
    }

    let respuesta: unknown = error.error;
    if (typeof respuesta === 'string') {
      const textoRespuesta = respuesta;
      try {
        respuesta = JSON.parse(textoRespuesta) as unknown;
      } catch {
        const detalle = textoRespuesta.trim();
        if (detalle) {
          return detalle;
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
