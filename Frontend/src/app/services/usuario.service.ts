import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from '../model/usuario';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private urlSalvar = 'http://localhost:8080/api/salvar-usuario';
  private urlLogin = 'http://localhost:8080/api/login';
  private urlUpdateMsgLida = 'http://localhost:8080/api/update_msg-Lida/:ID_ENVIOU/:ID_LEU';
  private urlRecuperarUsuarios = 'http://localhost:8080/api/todos-contatos/:ID_ORIGEM';

  constructor(
    private http: HttpClient
  ) { }

  salvar(usuario: any): Observable<any> {
    let result: Observable<Object>;
    result = this.http.post(this.urlSalvar, usuario);
    return result;
  }

  login(usuario: Usuario): Observable<any> {
    let result: Observable<Object>;
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    result = this.http.post(this.urlLogin, usuario, {headers, 'responseType' : 'text'});
    return result;
  }

  getTodosUsuario(idOrigem: number): Observable<any> {
    return this.http.get(this.urlRecuperarUsuarios.replace(':ID_ORIGEM', idOrigem.toString()));
  }

  /*IRA RECUPERAR TODAS AS MSG NAO LIDAS E SO DEPOIS ATUALIZAR POR ISSO NAO USEI O PUT*/
  updateMsgLida(idEnviou: number, idLeu: number): Observable<any> {
    let result: Observable<Object>;
    result = this.http.get(this.urlUpdateMsgLida.replace(':ID_ENVIOU', idEnviou.toString()).replace(':ID_LEU', idLeu.toString()));
    return result;
  }
}
