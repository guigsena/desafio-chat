import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MensagemService {

  private urlRecuperarMensagens = 'http://localhost:8080/api/todas-mensagens/:ID_ORIGEM/:ID_DESTINO';

  constructor(
    private http: HttpClient
  ) { }

  getTodasMensagens(idOrigem: number, idDestino: number): Observable<any> {
    const url = this.urlRecuperarMensagens.replace(':ID_ORIGEM', idOrigem.toString()).replace(':ID_DESTINO', idDestino.toString());
    return this.http.get(url);
  }
}
