import { Component, OnInit } from '@angular/core';
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { ActivatedRoute, Router } from '@angular/router';
import { UsuarioService } from '../services/usuario.service';
import { Mensagem } from '../model/mensagem';
import { Usuario } from '../model/usuario';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MensagemService } from '../services/mensagem.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  usuarioLogado = new Usuario();
  idUsuarioDestino: number;
  lstMensagens = Array<Mensagem>();
  disabled = true;
  exibeMsg = false;
  usuarios: Array<any>;
  mensagem: Mensagem;


  private stompClient = null;

  constructor(
    private route: ActivatedRoute,
    private usuarioService: UsuarioService,
    private mensagemService: MensagemService,
    private router: Router
    ) {
  }

  ngOnInit() {
    const helper = new JwtHelperService();

    const decodedToken = helper.decodeToken(localStorage.getItem('token'));
    this.usuarioLogado.id = decodedToken.sub;
    this.usuarioLogado.nome = decodedToken.nome;
    this.connect();
    //RECUPERA LISTA
    this.recuperaTodosUsuarios();

  }

  recuperaTodosUsuarios() {
    //RECUPERA USUARIOS CADASTRADOS
    this.usuarioService.getTodosUsuario(this.usuarioLogado.id).subscribe(data => {
      this.usuarios = data;
    });
  }

  abrirConversa(idUsuario) {
    this.idUsuarioDestino = idUsuario;

    const el = document.getElementsByClassName('chat_list active_chat');
    for (let i = 0; i < el.length; i++) {
      el[i].className = 'chat_list';
    }

    const usuSel = document.getElementById('el_' + idUsuario);
    usuSel.className = 'chat_list active_chat';
    this.exibeMsg = true;

    //INSTANCIA MENSAGEM
    this.mensagem = new Mensagem();
    this.lstMensagens = new Array<Mensagem>();

    const para = new Usuario();
    para.id = idUsuario;
    this.mensagem.para = para;

    this.mensagemService.getTodasMensagens(this.usuarioLogado.id, this.idUsuarioDestino).subscribe(data => {
      this.lstMensagens = data;
    });

    this.atualizaMsgParaLida();
  }

  setConnected(connected: boolean) {
    this.disabled = !connected;

    if (connected) {
      this.lstMensagens = new Array<Mensagem>();
    }
  }

  connect() {
    const socket = new SockJS('http://localhost:8080/api/ws');
    this.stompClient = Stomp.over(socket);

    const _this = this;

      this.stompClient.connect( {'Login': this.usuarioLogado.id}, function (frame) {
      _this.setConnected(true);


      _this.stompClient.subscribe('/user/queue/errors', function(message) {
        this.mensagem = new Mensagem();
        console.log('Error ' + message.body);
      });

      //ATUALIZA LISTA DE USUARIOS
      _this.stompClient.subscribe('/topic/reply', function(message) {
        _this.recuperaTodosUsuarios();
      });

      _this.atualizaUsuariosLogados();

      _this.stompClient.subscribe('/user/queue/reply', function(message) {
        if (JSON.parse(message.body).de.id === _this.idUsuarioDestino) {
          const msg: Mensagem = JSON.parse(message.body);
          msg.dataLeitura = new Date();
          _this.addMsg(msg);
        }
      });
    });
  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }

    this.setConnected(false);
    //REMOVE TOKEN
    localStorage.removeItem('token');
    console.log('Desconectado!');
    this.router.navigate(['/login']);
  }

  sendMsg() {
    const de = new Usuario();
    de.id = this.usuarioLogado.id;
    this.mensagem.de = de;
    this.mensagem.dataEnvio = new Date();
    /*ADD MSG*/
    this.addMsg(this.mensagem);
    /*ENVIA MSG PARA WEBSOCKET*/
    this.stompClient.send(
      '/app/message',
      {},
      /*TODO: mudar para passar obj mensagem*/
      // tslint:disable-next-line:max-line-length
      JSON.stringify({'conteudo':  this.mensagem.conteudo, 'de' : { 'id': this.usuarioLogado.id} , 'para': { 'id':  this.mensagem.para.id}, 'dataEnvio': this.mensagem.dataEnvio})
    );
    this.mensagem = new Mensagem();
  }

  /*ADICIONA MSG ENVIADA*/
  addMsg(message) {
    this.lstMensagens.push(message);
  }

  atualizaUsuariosLogados() {
      const data = JSON.stringify({
        'name' : this.usuarioLogado.nome
      });
      this.stompClient.send('/app/atualizalista', {}, data);
    }

  atualizaMsgParaLida() {
    this.usuarioService.updateMsgLida(this.idUsuarioDestino, this.usuarioLogado.id).subscribe(
      data => {
          if (data === 'true') {
            const usuD = this.usuarios.find(usu => usu.id === this.idUsuarioDestino);
            usuD.contMsgNaoLidas = 0;
          }
        }, error => {
          console.log('Erro ao atualizar mensagens não lidas.');
        }
    );
  }

}
