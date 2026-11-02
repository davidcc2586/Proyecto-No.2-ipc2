import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-paginacion-lista',
  styles: [`
    .pagination-button,
    .pagination-button:hover,
    .pagination-button:focus,
    .pagination-button:active,
    .pagination-button:disabled {
      color: #495057;
      background-color: #fff;
      border-color: #6c757d;
      box-shadow: none;
      opacity: 1;
    }
  `],
  template: `
    @if (totalPaginas > 1) {
      <nav class="d-flex justify-content-between align-items-center gap-2 mt-3" aria-label="Paginación">
        <small class="text-muted">
          Página {{ pagina + 1 }} de {{ totalPaginas }} · {{ totalElementos }} registros
        </small>
        <div class="btn-group btn-group-sm" role="group">
          <button
            class="btn btn-outline-secondary pagination-button"
            type="button"
            [disabled]="cargando || pagina === 0"
            (click)="cambiarPagina(pagina - 1)">
            Anterior
          </button>
          <button
            class="btn btn-outline-secondary pagination-button"
            type="button"
            [disabled]="cargando || pagina + 1 >= totalPaginas"
            (click)="cambiarPagina(pagina + 1)">
            Siguiente
          </button>
        </div>
      </nav>
    }
  `,
})
export class PaginacionLista {
  @Input() pagina = 0;
  @Input() totalPaginas = 0;
  @Input() totalElementos = 0;
  @Input() cargando = false;
  @Output() paginaCambiada = new EventEmitter<number>();

  cambiarPagina(pagina: number): void {
    if (pagina < 0 || pagina >= this.totalPaginas || this.cargando) {
      return;
    }
    this.paginaCambiada.emit(pagina);
  }
}
