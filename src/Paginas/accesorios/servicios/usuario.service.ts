import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UsuarioActualizarRequest } from '../../../Objetos/objetosRequest/UsuarioActualizarRequest';
import { UsuarioRequest } from '../../../Objetos/objetosRequest/UsuarioRequest';
import { Usuario } from '../../../Objetos/objetosResponse/Usuario';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private readonly baseUrl = '/Proyecto_2_ipc2/api/usuario';

  constructor(private readonly http: HttpClient) {}

  obtenerUsuariosPorRol(rolUsuario: string): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(
      `${this.baseUrl}/clasificar/${rolUsuario}`,
    );
  }

  obtenerUsuario(idUsuario: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.baseUrl}/datosUsuario/${idUsuario}`);
  }

  crearUsuario(usuario: UsuarioRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/crearCuentaUsuario`, usuario);
  }

  actualizarUsuario(usuario: UsuarioActualizarRequest): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/actualizarUsuario`, usuario);
  }

  cambiarEstado(idUsuario: number): Observable<{ mensaje: string }> {
    return this.http.patch<{ mensaje: string }>(
      `${this.baseUrl}/desactivarActivar/${idUsuario}`,
      null,
    );
  }
}
