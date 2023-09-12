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
<summary><b>Como Utilizar a API</b></summary>

1. Criem um personagem usando o endpoint `POST /personagem`:
    
   ```json
   {
     "nome": "string",
     "tipoClassePersonagem": "GUERREIRO"
   }
   ```
   O campo `tipoClassePersonagem` pode ser `GUERREIRO`, `BARBARO`, `CAVALEIRO`, `ORC`, `GIGANTE` ou `LOBISOMEM`.
2. Crie uma batalha usando o endpoint `POST /batalha`:
    
   ```json
   {
     "nomeJogadorAtacante": "string",
     "nomeMonstroDefensor": "string"
   }
   ```
   O campo `nomeMosnstroDefensor` é opcional. Se não for informado, um monstro aleatório será selecionado.

3. Realize um ataque usando o endpoint `PATCH /ataque/{uuidBatalha}`:
    - O campo `uuidBatalha` é o UUID da batalha que você deseja realizar os ataques.
    - O dados de ataque são rolados e o valor é calculado automaticamente para ambos personagens.
    - Após o ataque ser realizado, não é possivel realizar outro ataque no mesmo turno.
    - Após o ataque ser realizado, siga para a defesa dos personagens.

4. Realize uma defesa usando o endpoint `PATCH /defesa/{uuidBatalha}`:
    - O campo `uuidBatalha` é o UUID da batalha que você deseja realizar as defesas.
    - O dados de defesa são rolados e o valor é calculado automaticamente para ambos personagens.
    - Após a defesa ser realizada, não é possível realizar outra defesa no mesmo turno.
    - Após a defesa ser realizada, siga para o dano dos personagens.

5. Realize o dano usando o endpoint `PATCH /dano/{uuidBatalha}`:
    - O campo `uuidBatalha` é o UUID da batalha que você deseja realizar o dano.
    - O dano é calculado automaticamente para ambos personagens e a vida é atualizada.
    - Após o dano ser realizado, não é possível realizar outro dano no mesmo turno.
    - Após o dano ser realizado, siga para verificação do status da batalha.

6. Verifique se a batalha acabou e houve um vencedor usando o endpoint `PATCH /batalha/atualizar/{uuidBatalha}`:
    - O campo `uuidBatalha` é o UUID da batalha que você deseja verificar o status.
    - Se a batalha não acabou, o turno é atualizado e o próximo turno é iniciado.
    - Se a batalha acabou, o vencedor é definido e a batalha é finalizada.
    - Após a batalha ser finalizada, não é possível realizar mais nenhuma ação.

7. Consulte o histórico de batalhas usando o endpoint `GET /historico/completo/{uuidBatalha}`:
    - O campo `uuidBatalha` é o UUID da batalha que você deseja consultar o histórico.
    - O histórico é retornado em ordem cronológica de turno.
    - Para um histórico resumido, utilize o endponit `GET /historico/resumso/{uuidBatalha}`. 

</details>

<details>
<summary><b>Diagrama Entidade Relacionamento(DER)</b></summary>

![DER-Avanade-Rpg.jpg](..%2F..%2F..%2FPictures%2FDER-Avanade-Rpg.jpg)

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

