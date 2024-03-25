# Estórias de Usuário - Sistema de Usuários e Carros API RESTful

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

### 3. Exclusão de Conta
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


## Observações Finais

