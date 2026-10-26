import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Estado } from '../../../Objetos/enums/Estado';
import { LibroActualizarRequest } from '../../../Objetos/objetosRequest/LibroActualizarRequest';
import { LibroRequest } from '../../../Objetos/objetosRequest/LibroRequest';
import { Libro } from '../../../Objetos/objetosResponse/Libro';
import { BibliotecaService } from '../../accesorios/servicios/biblioteca.service';

interface FormularioLibro {
  autor: string;
  anio: string;
  titulo: string;
  editorial: string;
  cantidad: string;
}

@Component({
  selector: 'app-catalogo-libros',
  imports: [FormsModule],
  templateUrl: './catalogoLibros.html',
})
export class CatalogoLibros implements OnInit {
  libros: Libro[] = [];
  libroSeleccionado: Libro | null = null;
  modo: 'crear' | 'editar' = 'crear';
  formulario: FormularioLibro = this.formularioVacio();
  cargando = false;
  guardando = false;
  mensaje = '';
  error = '';

  constructor(
    private readonly bibliotecaService: BibliotecaService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.cargarCatalogo();
  }

  cargarCatalogo(): void {
    this.cargando = true;
    this.error = '';
    this.bibliotecaService.obtenerCatalogo().subscribe({
      next: (libros) => {
        this.libros = libros;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.error = this.mensajeError(error, 'No se pudo cargar el catálogo.');
        this.changeDetector.markForCheck();
      },
    });
  }

  guardarLibro(formulario: NgForm): void {
    this.error = '';
    this.mensaje = '';
    if (formulario.invalid) {
      formulario.control.markAllAsTouched();
      this.error = 'Completa todos los campos obligatorios.';
      return;
    }

    this.guardando = true;
    if (this.modo === 'crear') {
      const request = new LibroRequest(
        this.formulario.autor,
        this.formulario.anio,
        this.formulario.titulo,
        this.formulario.editorial,
        this.formulario.cantidad,
      );
      this.bibliotecaService.registrarLibro(request).subscribe({
        next: () => {
          this.guardando = false;
          this.mensaje = 'El libro se registró correctamente.';
          formulario.resetForm();
          this.formulario = this.formularioVacio();
          this.cargarCatalogo();
          this.changeDetector.markForCheck();
        },
        error: (error: HttpErrorResponse) => {
          this.guardando = false;
          this.error = this.mensajeError(error, 'No se pudo registrar el libro.');
          this.changeDetector.markForCheck();
        },
      });
      return;
    }

    if (!this.libroSeleccionado) {
      this.guardando = false;
      this.error = 'Selecciona un libro para editar.';
      return;
    }

    const request = new LibroActualizarRequest(
      String(this.libroSeleccionado.idLibro),
      this.formulario.autor,
      this.formulario.anio,
      this.formulario.titulo,
      this.formulario.editorial,
    );
    this.bibliotecaService.actualizarLibro(request).subscribe({
      next: () => {
        this.guardando = false;
        this.mensaje = 'Los datos del libro se actualizaron correctamente.';
        this.cancelarEdicion();
        this.cargarCatalogo();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.guardando = false;
        this.error = this.mensajeError(error, 'No se pudo actualizar el libro.');
        this.changeDetector.markForCheck();
      },
    });
  }

  editarLibro(libro: Libro): void {
    this.error = '';
    this.mensaje = '';
    this.bibliotecaService.obtenerLibro(libro.idLibro).subscribe({
      next: (detalle) => {
        this.libroSeleccionado = detalle;
        this.modo = 'editar';
        this.formulario = {
          autor: detalle.autor ?? '',
          anio: String(detalle.anio ?? ''),
          titulo: detalle.titulo ?? '',
          editorial: detalle.editorial ?? '',
          cantidad: String(detalle.cantidad ?? ''),
        };
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.error = this.mensajeError(error, 'No se pudieron consultar los datos del libro.');
        this.changeDetector.markForCheck();
      },
    });
  }

  cambiarEstado(libro: Libro): void {
    if (!window.confirm(`¿Deseas cambiar el estado de "${libro.titulo}"?`)) {
      return;
    }
    this.bibliotecaService.cambiarEstadoLibro(libro.idLibro).subscribe({
      next: () => {
        this.mensaje = 'El estado del libro se actualizó correctamente.';
        this.cargarCatalogo();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.error = this.mensajeError(error, 'No se pudo cambiar el estado del libro.');
        this.changeDetector.markForCheck();
      },
    });
  }

  eliminarLibro(libro: Libro): void {
    if (!window.confirm(`¿Deseas eliminar "${libro.titulo}" del catálogo?`)) {
      return;
    }
    this.bibliotecaService.eliminarLibro(libro.idLibro).subscribe({
      next: () => {
        this.mensaje = 'El libro se eliminó del catálogo.';
        this.cargarCatalogo();
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.error = this.mensajeError(error, 'No se pudo eliminar el libro.');
        this.changeDetector.markForCheck();
      },
    });
  }

  cancelarEdicion(): void {
    this.modo = 'crear';
    this.libroSeleccionado = null;
    this.formulario = this.formularioVacio();
  }

  estaActivo(estado: Estado | string): boolean {
    return String(estado).toLocaleLowerCase() === 'activo';
  }

  private formularioVacio(): FormularioLibro {
    return { autor: '', anio: '', titulo: '', editorial: '', cantidad: '' };
  }

  private mensajeError(error: HttpErrorResponse, alternativo: string): string {
    if (error.status === 0) {
      return 'No se pudo conectar con el backend. Verifica que esté iniciado y que el proxy esté activo.';
    }
    const cuerpo = error.error;
    if (typeof cuerpo === 'string' && cuerpo.trim()) {
      try {
        const json = JSON.parse(cuerpo) as { error?: string; mensaje?: string };
        return json.error || json.mensaje || cuerpo;
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
