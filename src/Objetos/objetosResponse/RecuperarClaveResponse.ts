export class RecuperarClaveResponse {
  clave: string | null = null;

  constructor();
  constructor(clave: string);
  constructor(clave?: string) {
    if (clave !== undefined) {
      this.clave = clave;
    }
  }
}
