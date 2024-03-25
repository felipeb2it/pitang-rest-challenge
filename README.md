# Estórias de Usuário - Sistema de Usuários e Carros API RESTful

Este documento detalha as estórias de usuário desenvolvidas para o desafio de criação de uma API RESTful para um sistema de usuários e carros.

## Estórias de Usuário

### 1. Registro de Usuário
**Como** usuário não autenticado,  
**Quero** poder me registrar na aplicação,  
**Para** criar uma nova conta fornecendo meus dados pessoais e de meu(s) carro(s).

#### Critérios de Aceitação:
- Validação dos dados de entrada.
- Criptografia de senha.
- Geração de token JWT após registro.
- Possibilidade de registrar um ou mais carros.

### 2. Listagem de Carros
**Como** usuário autenticado,  
**Quero** listar todos os meus carros,  
**Para** visualizar todos os veículos associados à minha conta.

#### Critérios de Aceitação:
- Autenticação via token JWT.
- Retorno de todos os carros do usuário autenticado.

### 3. Adição de Carro
**Como** usuário autenticado,  
**Quero** adicionar um novo carro à minha conta,  
**Para** expandir minha lista de veículos.

#### Critérios de Aceitação:
- Autenticação necessária.
- Fornecimento dos detalhes do carro.
- Associação do carro ao usuário autenticado.

### 4. Remoção de Carro
**Como** usuário autenticado,  
**Quero** remover um carro específico,  
**Para** manter minha lista de veículos atualizada.

#### Critérios de Aceitação:
- Autenticação necessária.
- O carro pertence ao usuário autenticado.

### 5. Atualização de Carro
**Como** usuário autenticado,  
**Quero** atualizar os detalhes de um carro,  
**Para** corrigir ou modificar suas informações.

#### Critérios de Aceitação:
- Autenticação necessária.
- Fornecimento dos novos detalhes do carro.

### 6. Atualização de Perfil de Usuário
**Como** usuário autenticado,  
**Quero** visualizar e atualizar minhas informações pessoais,  
**Para** manter meu perfil atualizado.

#### Critérios de Aceitação:
- Autenticação necessária.
- Permissão para atualizar informações pessoais.

### 7. Exclusão de Conta
**Como** usuário autenticado,  
**Quero** excluir minha conta,  
**Para** remover minhas informações do sistema.

#### Critérios de Aceitação:
- Autenticação necessária.
- Remoção de todos os dados do usuário.

### 8. Listagem de Todos os Usuários (Admin)
**Como** administrador,  
**Quero** listar todos os usuários,  
**Para** ter uma visão geral dos usuários registrados.

#### Critérios de Aceitação:
- Restrição de acesso ao administrador.
- Inclusão de detalhes básicos dos usuários.

### 9. Upload de Fotos (Bônus)
**Como** usuário autenticado,  
**Quero** fazer upload de fotos para meu perfil e para meus carros,  
**Para** personalizar minha conta.

#### Critérios de Aceitação:
- Autenticação necessária.
- Possibilidade de upload para o perfil do usuário e carros.

## Observações Finais

Estas estórias de usuário cobrem as funcionalidades principais e extras propostas pelo desafio. Elas são a base para o desenvolvimento iterativo do projeto, seguindo princípios ágeis.
