# Estórias de Usuário - Pitang API RESTful Desafio Técnico

Este documento detalha as estórias de usuário desenvolvidas para o desafio de criação de uma API RESTful para um sistema de usuários e carros.

## Estórias de Usuário

### 1. Registro de Usuário
**Como** usuário não autenticado,  
**Quero** poder me registrar na aplicação,  
**Para** criar uma nova conta fornecendo meus dados pessoais e de meu(s) carro(s).

#### Critérios de Aceitação:
- Validação dos dados de entrada.
- Possibilidade de registrar um ou mais carros.

### 2. Atualização de Perfil de Usuário
**Como** usuário não autenticado,  
**Quero** visualizar e atualizar minhas informações pessoais,  
**Para** manter meu perfil atualizado.

#### Critérios de Aceitação:
- Permissão para atualizar informações pessoais.

### 3. Exclusão de Conta do usuário
**Como** usuário não autenticado,  
**Quero** excluir minha conta,  
**Para** remover minhas informações do sistema.

#### Critérios de Aceitação:
- Remoção de todos os dados do usuário.

### 4. Listagem de Todos os Usuários
**Como** usuário não autenticado,
**Quero** listar todos os usuários,  
**Para** ter uma visão geral dos usuários registrados.

#### Critérios de Aceitação:
- Listar todos os detalhes dos usuários.

### 5. Listagem de Carros
**Como** usuário autenticado,  
**Quero** listar todos os meus carros,  
**Para** visualizar todos os veículos associados à minha conta.

#### Critérios de Aceitação:
- Autenticação via token JWT.
- Retorno de todos os carros do usuário autenticado.

### 6. Adição de Carro
**Como** usuário autenticado,  
**Quero** adicionar um novo carro à minha conta,  
**Para** expandir minha lista de veículos.

#### Critérios de Aceitação:
- Autenticação necessária.
- Fornecimento dos detalhes do carro.
- Associação do carro ao usuário autenticado.

### 7. Remoção de Carro
**Como** usuário autenticado,  
**Quero** remover um carro específico,  
**Para** manter minha lista de veículos atualizada.

#### Critérios de Aceitação:
- Autenticação necessária.
- O carro pertence ao usuário autenticado.

### 8. Atualização de Carro
**Como** usuário autenticado,  
**Quero** atualizar os detalhes de um carro,  
**Para** corrigir ou modificar suas informações.

#### Critérios de Aceitação:
- Autenticação necessária.
- Fornecimento dos novos detalhes do carro.



# Solução

Desenvolvi a solução utilizando o Spring-boot, seguindo as melhores práticas, principalmente os princípios do SOLID.
O projeto ficou dividido em 3 camadas principais, sendo Controllers, Services e Repositories. As configurações, Handlers e Filters ficaram no package org.pitang.rest.challenge

Nos DTOs utilizei o recurso "Record" do Java nos bodies de entrada de dados e para retorno de erros, eles permitem a criação de objetos de forma mais eficiente do que é feito com classes. Nas entities usei o JSONIgnore nos campos em que normarmente não são exibidos e quando necessári a exibição, utlilizei Projections nos Repositories.

Utilizei anotações para validação de campos e um Validation Handler para customizar o retorno quando faltam campos no request ou são inválidos, desta forma consegui mais flexibilidade para controlar as validações de campos.

Fiz os testes unitários dos Controllers, UserController e CarController. Neste projeto resolvi me dedicar a cumprir com todas as funcionalidades e por isso a cobertura de código esta baixa mas em grandes projetos é essencial termos uma cobertura de código de pelo menos 80%.

Criei as estruturas pedidos no **Requisito extra**, a de usuários esta no UserController(método usersBonusOrdered) e a de carros esta no CarController(método carsBonusOrdered). Utilizei streams e Lambda e a solução implementa todas as regras do requisito.



# Pitang API RESTful Desafio Técnico

Desafio técnico para testar a proficiência dos canditados em projetos de APIs REST

## Pré-requisitos

- Java 21 ou superior
- Maven
- Spring Boot
- Banco de dados H2(in-memmory)

## Configuração

1. **Clone o repositório**:

   ```bash
   git clone [URL_DO_REPOSITÓRIO]
   cd [NOME_DO_PROJETO]]
   ```

## Construção e Execução

Instruções passo a passo para construir e executar o projeto.

1. **Build do projeto com Maven**:

   ```bash
   mvn clean install
   ````

2. **Executar a aplicação Spring Boot**:

   ```bash
   mvn -boot:runnspring
   ````


## Testes

Como executar os testes automatizados para este projeto.

```bash
mvn test
````


## Documentação da API

Instruções sobre como acessar a documentação da API, se disponível. Se você estiver usando o Swagger, por exemplo, inclua o URL para acessar a UI do Swagger.

```plaintext
http://localhost:8080/swagger-ui/index.html
```
.




