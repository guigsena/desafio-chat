Chat - Web com Spring Boot

Para maior praticidde foi utilizado o banco H2 em memória

Detalhes da aplicação - Back End
Java / Spring Boot
WebSocket
Swagger
Hibernate (JPA)
Spring Security
Maven

Para executar a aplicação backend local é nencessário:
Instalação do Java, disponível no site da oracle (http://www.oracle.com)
Para a importação das dependências do projeto é necessario ter o maven instalado junto a ide

Detalhes da aplicação - Front End
Angular 6

Para executar a aplicação frontend local é nencessário:
Instalação do node (https://nodejs.org)
Instalação do Angular mais recente pelo seguinte comando no prompt(npm install @angular/cli -g) 
para que o projeto frontend baixe todas as depedências é necessario a execução do seguinte comando dentro da pasta do projeto (npm install)

Paara inicializar o frontend é necessário a execução do seguinte comando (ng serve)

O que foi implementado:

Cadastro de usuários (email, nome e telefone)
Uma tela de login para que o usuário cadastrado acesse a aplicação. O sistema já inicia com dois usuários cadastros previamente (joaopedro@gmail.com, "anamaria@gmail.com") ambos com a senha 123.
Ao logar o usuário automaticamente tem acesso ao chat, contendo a direita todos os usuarios cadastrados demonstrando quais estão online e quais não estão.
Ao clicar em um usuário é possivel enviar uma mensagem especifica para ele.

O que falta implementar:

Bloqueio de contatos.
Envio de imagens e videos.
Envio de mensagens para todos os usuários.