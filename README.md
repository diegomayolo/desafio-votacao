
<h1 align="center">Sistema de Votação</h1>

## Sobre o Projeto

Este sistema foi desenvolvido com o intuito de permitir a votação de associados em assembléias.

## Funcionalidades

- Cadastrar e consultar associados, pautas e sessões.
- Registrar votos em pautas de sessões ativas.
- Consultar resultados de pautas.

## Tecnologias

- <b>Linguagem de Programação:</b>	Java 17
- <b>Banco de Dados:</b> PostgreSQL
- <b>Framework:</b> Spring Boot
- <b>Integração de APIs:</b> Feign Client
- <b>Testes de Integração:</b>	MockMvc
- <b>Métricas e Monitoramento:</b>	Spring Boot Actuator
- <b>Documentação da API:</b> Swagger

## Pré-Requisitos e Inicialização

- Subir o Banco de Dados:

    - Certifique-se de que o Docker está instalado e em execução.
      No diretório raiz do projeto, execute o comando abaixo para iniciar o banco de dados: <code>docker-compose up -d</code>
      Esse comando usa o docker-compose.yml para configurar e inicializar o banco de dados necessário para a aplicação em segundo plano (-d indica modo "detached").

- Iniciar a Aplicação:

    - Após o banco de dados estar em execução, você pode iniciar a aplicação.
      Execute o seguinte comando: <code>mvn spring-boot:run</code> Esse comando compila e executa a aplicação usando o Maven e o Spring Boot.

## Regras

- Não é permitido cadastrar associados com CPF inválido ou duplicado.
- Somente uma sessão ativa por pauta.
- Associados podem votar apenas em sessões ativas.
- Cada associado pode votar uma única vez na pauta.
- Pautas podem ter nova contagem de votos com uma nova sessão.

## Teste dos Endpoints

Você pode testar os endpoints em qualquer ferramenta de requisições, como Postman ou Insomnia.
Além disso, se você não possui nenhuma dessas ferramentas, os testes também podem ser realizados diretamente através da interface do <a href="http://localhost:8080/swagger-ui/index.html">Swagger</a>.

## Documentação da API

A API está documentada com Swagger e pode ser acessada para a visualização e teste dos endpoints. Basta acessar a interface interativa do Swagger no link abaixo:

- http://localhost:8080/swagger-ui/index.html

## Métricas e Monitoramento com Spring Boot Actuator

Após iniciar a aplicação, você pode acessar várias métricas sobre o desempenho e saúde do sistema através do endpoint:

- http://localhost:8080/actuator

As métricas relacionadas aos endpoints da aplicação, como a duração das chamadas e a quantidade de requisições, podem ser consultadas em:

- http://localhost:8080/actuator/metrics

## Versionamento da API

A versão da API é definida pela URL, permitindo visibilidade clara e controle das mudanças. Isso assegura que diferentes versões da API possam coexistir sem impactar os usuários.

## Considerações

Sistema desenvolvido para votação de associados em assembléias. Inclui validações, testes e integrações com Feign Client para validação de CPFs. Utilizado banco H2 para testes e Swagger para documentação.
