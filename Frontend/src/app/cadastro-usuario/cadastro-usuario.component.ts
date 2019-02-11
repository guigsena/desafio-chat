import { Component, OnInit } from '@angular/core';
import { Usuario } from '../model/usuario';
import { UsuarioService } from '../services/usuario.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-cadastro-usuario',
  templateUrl: './cadastro-usuario.component.html',
  styleUrls: ['./cadastro-usuario.component.css']
})
export class CadastroUsuarioComponent implements OnInit {

  usuario: Usuario;

  constructor(
    private usuarioService: UsuarioService,
    private _location: Location
  ) { }

  ngOnInit() {
    this.usuario = new Usuario();
  }

  salvarUsuario() {
    this.usuarioService.salvar(this.usuario).subscribe(data => {
      this.usuario = data;
      this.exibeMsg(true);
      this.usuario = new Usuario();
    }, error => {
      this.exibeMsg(false);
    });
  }

  voltar() {
    this._location.back();
  }

  exibeMsg(isMsgSucesso) {
    const divMsg = document.getElementById('divMsg');

    if (isMsgSucesso) {
      divMsg.className = 'alert alert-success';
      divMsg.innerHTML = 'Usuário cadastrado com sucesso!';
    } else {
      divMsg.className = 'alert alert-danger';
      divMsg.innerHTML = 'Erro ao cadastrar usuário!';
    }
  }
}
