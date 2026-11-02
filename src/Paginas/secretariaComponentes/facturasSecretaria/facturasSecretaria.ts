import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Factura } from '../../../Objetos/objetosResponse/Factura';
import { FacturaService } from '../../accesorios/servicios/factura.service';

@Component({
  selector: 'app-facturas-secretaria',
  templateUrl: './facturasSecretaria.html',
})
export class FacturasSecretaria implements OnInit {
  facturas: Factura[] = [];
  cargando = false;
  error = '';

  constructor(
    private readonly facturaService: FacturaService,
    private readonly changeDetector: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.cargarFacturas();
  }

  cargarFacturas(): void {
    this.cargando = true;
    this.error = '';
    this.facturaService.obtenerFacturas().subscribe({
      next: (facturas) => {
        this.facturas = facturas;
        this.cargando = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.error = this.mensajeError(error);
        this.changeDetector.markForCheck();
      },
    });
  }

  private mensajeError(error: HttpErrorResponse): string {
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
    return `No se pudieron cargar las facturas (HTTP ${error.status}).`;
  }
}
