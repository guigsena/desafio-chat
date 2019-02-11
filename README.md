# Chat - Web com Spring Boot

O sistema foi desenvolvido em java (backend) angular 6 (frontend) e banco de dados utilizado foi o mysql

## Detalhes da aplicação - Back End
Java / Spring Boot
WebSocket
Swagger
Hibernate (JPA)
Spring Security
Maven

## Para executar a aplicação backend local é nencessário:
Instalação do Java, disponível no site da oracle (http://www.oracle.com)
Para a importação das dependências do projeto é necessario ter o maven instalado junto a ide

## Detalhes da aplicação - Front End
Angular 6 (Websocket STOMP)

## Para executar a aplicação frontend local é nencessário:
Instalação do node (https://nodejs.org)
Instalação do Angular mais recente pelo seguinte comando no prompt(npm install @angular/cli -g) 
para que o projeto frontend baixe todas as depedências é necessario a execução do seguinte comando dentro da pasta do projeto (npm install)

Para inicializar o frontend é necessário a execução do seguinte comando (ng serve)

# O que foi implementado:

Cadastro de usuários (email, nome e telefone)
Uma tela de login para que o usuário cadastrado acesse a aplicação. O sistema já inicia com dois usuários cadastros previamente (joaopedro@gmail.com, "anamaria@gmail.com") ambos com a senha 123.
Ao logar o usuário automaticamente tem acesso ao chat, contendo a direita todos os usuarios cadastrados (lista de contatos) demonstrando quais estão online e quais não estão.
Ao clicar em um usuário é possivel enviar uma mensagem especifica para ele.
Documentação so banckend com swagger. Para ter acesso à documentação gerada basta acessar o link(http://localhost:8080/swagger-ui.html)

# O que falta implementar:
Disponibilizar a aplicação funcionando em algum serviço de nuvem (Heroku, AWS, etc)

Criar solicitação de amizade (Em um primeiro momento o usuário pode enviar mensagens para todos usuarios cadastrados).
Bloqueio e desbloqueio de usuários.
Edição de mensagens enviadas.
Envio de mensagens de imagem, áudio e vídeo.
Testes unitários.

# Melhorias
Filtros dos CORS e do spring security
Refatorar principalmente a Classe TokenAuthenticationService
Atualizar a lista de contatos onlines/offlines quando um usuário desconectar.
