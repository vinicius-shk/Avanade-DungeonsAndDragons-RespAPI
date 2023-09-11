# Avanade Dungeons & Dragons Rest API

## Descrição do Projeto

A Avanade Dungeons & Dragons Rest API é uma API REST que simula um jogo no estilo Dungeons & Dragons (D&D). A aplicação
oferece funcionalidades completas, incluindo um CRUD de personagens, criação de batalhas, realização de ações por turnos
e um histórico de batalhas.

## Tecnologias Utilizadas

A seguir, estão algumas das stacks relevantes utilizadas neste projeto:

- **Spring Boot Starter Data JPA**: Facilita a integração com banco de dados usando o padrão JPA (Java Persistence API).

- **Spring Boot Starter Web**: Facilita a criação de aplicativos web com o Spring Boot.

- **PostgreSQL Driver**: Driver para integração com o banco de dados PostgreSQL.

- **Lombok**: Simplifica a criação de classes Java com anotações para geração automática de métodos como getters e
  setters.

- **Springdoc OpenAPI Starter WebMvc UI**: Integração com o Swagger para documentação da API usando Spring Web MVC.

- **Spring Boot Starter Validation**: Facilita a validação de dados de entrada.

<details>
<summary><b>Como Iniciar o Projeto</b></summary>

Siga os passos abaixo para executar o projeto localmente:

1. Clone este repositório:

   ```bash
   git clone git@github.com:vinicius-shk/Avanade-DungeonsAndDragons-RestAPI.git
   ```

2. Abra o projeto em sua IDE de preferência.

3. Atualize as dependências do Maven.

4. No terminal, navegue até a raiz do projeto e execute o seguinte comando para iniciar o banco de dados em Docker:

   ```bash
   docker-compose up -d
   ```

   Isso inicializará o banco de dados em um contêiner Docker.
5. Inicie o projeto em sua IDE de preferência.

</details>

<details>
<summary><b>Diagrama Entidade Relacionamento(DER)</b></summary>

![DER-Avanade-Rpg](https://github.com/vinicius-shk/Avanade-DungeonsAndDragons-RestAPI/assets/102389527/9d9c9695-e779-4529-86d7-5b92c38095dd)

</details>

<details>
<summary><b>Documentação</b></summary>

A documentação da API pode ser acessada pelo link [Documentação da API](http://localhost:8080/swagger-ui/index.html)
após iniciar o projeto localmente.

</details>

## Conclusões Finais

Este projeto foi desenvolvido como parte do processo seletivo da Avanade em parceria com a AdaTech para a vaga de
Desenvolvedor Java Backend Pleno. A aplicação foi desenvolvida em Java 17 com Spring Boot 3.1.3 e banco de dados PostgreSQL. A API foi documentada com
Swagger e o banco de dados foi executado em Docker. O projeto foi uma ótima oportunidade para aprender mais sobre o ecossistema Spring e a linguagem Java.

