import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioService } from '../services/usuario.service';
import { Usuario } from '../model/usuario';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  usuario: Usuario;

  constructor(
    private router: Router,
    private usuarioService: UsuarioService) { }

  ngOnInit() {
    this.usuario = new Usuario();
  }

  logar() {
    this.usuarioService.login(this.usuario).subscribe(
      data => {
        if (data != null && data !== '' && data !== undefined) {
          localStorage.setItem('token', data);
          this.router.navigate(['/chat']);
        } else {
          this.exibeMsg(false);
        }
      }
    );
  }

  exibeMsg(isMsgSucesso) {
    const divMsg = document.getElementById('divMsg');

    if (!isMsgSucesso) {
      divMsg.className = 'alert alert-danger';
      divMsg.innerHTML = 'E-mail e ou senha inválido(s)!';
    }
  }

  novoCadastro() {
    this.router.navigate(['/cadastro-usuario']);
  }
}
