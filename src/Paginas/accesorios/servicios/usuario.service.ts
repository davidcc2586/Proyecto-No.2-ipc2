import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UsuarioActualizarRequest } from '../../../Objetos/objetosRequest/UsuarioActualizarRequest';
import { UsuarioRequest } from '../../../Objetos/objetosRequest/UsuarioRequest';
import { Usuario } from '../../../Objetos/objetosResponse/Usuario';
import { UsuarioSelectResponse } from '../../../Objetos/objetosResponse/UsuarioSelectResponse';
import { PaginaResponse } from '../../../Objetos/objetosResponse/PaginaResponse';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private readonly baseUrl = '/Proyecto_2_ipc2/api/usuario';

  constructor(private readonly http: HttpClient) {}

  obtenerUsuariosPorRol(rolUsuario: string): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(
      `${this.baseUrl}/clasificar/${rolUsuario}`,
    );
  }

  obtenerUsuariosPorRolPaginados(rolUsuario: string,pagina: number,tamano = 7,): Observable<PaginaResponse<Usuario>> {
    return this.http.get<PaginaResponse<Usuario>>(
      `${this.baseUrl}/clasificarPaginado/${encodeURIComponent(rolUsuario)}`,
      { params: { pagina, tamano } },
    );
  }

  obtenerUsuario(idUsuario: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.baseUrl}/datosUsuario/${idUsuario}`);
  }

  obtenerUsuariosSelect(rolUsuario: string): Observable<UsuarioSelectResponse[]> {
    return this.http.get<UsuarioSelectResponse[]>(
      `${this.baseUrl}/selects/${encodeURIComponent(rolUsuario)}`,
    );
  }

  obtenerSecretariosSelect(): Observable<UsuarioSelectResponse[]> {
    return this.http.get<UsuarioSelectResponse[]>(`${this.baseUrl}/selectSecretarios`);
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
