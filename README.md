# InsightFullstackChallenge

## Bem vindos ao repositório do desafio de Fullstack da Insight!

### O que é o desafio?

O desafio consiste em desenvolver uma aplicação web que permita a criação de um sistema de gestão de fornecedores de uma empresa. A aplicação deve ser composta por um frontend e um backend, e deve ser possível realizar as seguintes operações: 

- Cadastrar um fornecedor
- Listar todos os fornecedores
- Atualizar um fornecedor
- Deletar um fornecedor
- Visualizar um fornecedor

### Tecnologias utilizadas

- Frontend: `Nextjs`, `Axios`, `TailwindCSS`, `Ant Design`, `Yup`

- Backend: `Spring Boot`, `Spring Data JPA`, `Spring Web`, `Spring Security`, `MongoDB`, `Lombok`, `Swagger`

- Configurações adicionais: `Docker`, `Docker` `Compose`
  - Para facilitar a execução da aplicação, foi utilizado o Docker e Docker Compose. Também foi utilizado o `nginx` para servir o frontend e o backend na máquina virtual.
  - O Sistema também foi configurado para rodar em um ambiente de produção no `google cloud plataform` utilizando servidores dns e https.

### Como executar a aplicação?

Primeiramente é necessário clonar o repositório em sua máquina. Para isso, basta executar o comando `git clone https://github.com/Juandbpimentel/InsightFullstackChallenge.git` no terminal.

### Dependências

Para executar a aplicação, é necessário ter o Docker e o Docker Compose instalados na máquina. Também é necessário instalar o Maven para compilar o backend que será utilizado no container do docker.

Depois de instalar o Maven, basta executar o comando `mvn clean install` na pasta do backend para compilar o projeto.

### Executando a aplicação

Após a instalação e compilação do backend, basta executar o comando `docker-compose up` na raiz do projeto. O frontend estará disponível em `http://localhost:3000` e o backend em `http://localhost:8080`, mas também pode ser configurado via variáveis de ambiente do docker-compose.yml.

### Acessando a aplicação

Para acessar a aplicação frontend, basta acessar o endereço `http://localhost:3000` no navegador. A aplicação estará disponível para uso.

Para vizualizar a documentação da API, basta acessar o endereço `http://localhost:8080/api/docs-ui` no navegador.

Para acessar a aplicação em produção, basta acessar o endereço `https://insightchallenge.juanpimentel.dev.br` no navegador.

Os endpoins de acesso são os mesmos da aplicação local.

### Documentação extra
- Arquivo de Modelagem de Dados: [Modelagem de Dados](./docs/DiagramaInsight.pdf)
