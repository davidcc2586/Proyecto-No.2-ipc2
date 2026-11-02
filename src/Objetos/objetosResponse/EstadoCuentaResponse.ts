import { Pago } from './Pago';

export interface EstadoCuentaResponse {
  anioLectivo: number;
  pagos: Pago[];
  mesesPendientes: number[];
}
