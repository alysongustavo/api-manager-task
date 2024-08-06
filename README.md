# API REST de Gerenciamento de Tarefas

![version](https://img.shields.io/badge/version-1.0.0-blue.svg)

## Descrição

Esta API REST foi desenvolvida utilizando Spring Boot 3.3.2 e tem como objetivo gerenciar tarefas, categorias e usuários. A aplicação utiliza um banco de dados PostgreSQL e está containerizada com Docker para ser disponibilizada em um cluster Kubernetes.

## Endpoints

### Task Controller

**Descrição**: Controlador que gerencia as funcionalidades da entidade `Task`.

- **GET /tasks**
    - Retorna todas as tarefas.

- **GET /tasks/{id}**
    - Retorna uma tarefa específica pelo ID.

- **PUT /tasks/{id}**
    - Atualiza uma tarefa específica pelo ID.

- **DELETE /tasks/{id}**
    - Deleta uma tarefa específica pelo ID.

- **POST /tasks**
    - Cria uma nova tarefa.

### Category Controller

**Descrição**: Controlador que gerencia as funcionalidades da entidade `Category`.

- **GET /categories**
    - Retorna todas as categorias.

- **GET /categories/{id}**
    - Retorna uma categoria específica pelo ID.

- **PUT /categories/{id}**
    - Atualiza uma categoria específica pelo ID.

- **DELETE /categories/{id}**
    - Deleta uma categoria específica pelo ID.

- **POST /categories**
    - Cria uma nova categoria.

### User Controller

**Descrição**: Controlador que gerencia as funcionalidades da entidade `User`.

- **GET /users**
    - Retorna todos os usuários.

- **GET /users/{id}**
    - Retorna um usuário específico pelo ID.

- **PUT /users/{id}**
    - Atualiza um usuário específico pelo ID.

- **DELETE /users/{id}**
    - Deleta um usuário específico pelo ID.

- **POST /users**
    - Cria um novo usuário.

### Authentication Controller

**Descrição**: Controlador responsável pelo cadastro e autenticação de usuários utilizando JWT.

- **POST /auth/signup**
    - Realiza o cadastro de um novo usuário.

- **POST /auth/login**
    - Realiza o login de um usuário e retorna um token JWT.

## Tecnologias Utilizadas

- **Spring Boot 3.3.2**
    - Framework para desenvolvimento da aplicação backend.

- **PostgreSQL**
    - Banco de dados utilizado pela aplicação.

- **Docker**
    - Ferramenta de containerização utilizada para empacotar a aplicação.

- **Kubernetes**
    - Plataforma de orquestração de containers utilizada para disponibilizar a aplicação.

## Como Executar a Aplicação

### Pré-requisitos

- Docker e Docker Compose instalados.
- Kubernetes configurado e em execução.

### Instalação do Kind e Criação do Cluster

#### Instalação do Kind

1. **Baixe e instale o Kind:**

```sh
curl -Lo ./kind https://kind.sigs.k8s.io/dl/v0.11.1/kind-linux-amd64
chmod +x ./kind
sudo mv ./kind /usr/local/bin/kind
```

2. **Verifique a instalação:**

```sh
kind version
```

3. **Criação do Cluster**
   1. Crie o cluster utilizando o arquivo kind-config.yaml localizado na pasta k8s/kind-config.yaml:

```sh
kind create cluster --name meu-cluster --config k8s/kind-config.yaml
```

### Passos para Execução

4. **Clone o repositório**

```sh
git clone https://github.com/alysongustavo/api-manager-task.git
cd api-manager-task
```

5. **Compile e construa a imagem Docker**

```sh
./mvnw clean package
docker build -t minha-aplicacao-spring:latest .
```

6. **Suba a imagem para o Docker Hub**

```sh
docker tag minha-aplicacao-spring:latest meu-usuario/minha-aplicacao-spring:latest
docker push meu-usuario/minha-aplicacao-spring:latest
```

7. **Implante a aplicação no Kubernetes**

```sh
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/backend-service.yaml
```

8. **Acesse a aplicação**

A aplicação estará disponível no endereço configurado no seu serviço Kubernetes.


9. **Contato**

Para mais informações, entre em contato pelo email: g.dasilva@hotmail.com