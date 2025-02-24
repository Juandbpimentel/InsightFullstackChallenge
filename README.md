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

### Configurando Nginx

## Adicionar configurações ao `nginx_default.conf`

   Para configurar o nginx, basta acessar a pasta `nginx` e alterar o arquivo `default.conf` com as configurações desejadas. Depois, basta executar o comando `docker-compose up` na pasta `nginx` para subir o servidor.
   
   Adicione as configurações do subdomínio `seusubdominio.com.br`: Crie ou edite o arquivo de configuração do site em `/etc/nginx/sites-available/seusubdominio` e adicione as configurações:
   ```nginx
   http {
       server {
           listen 80;
           listen [::]:80;
           server_name seusubdominio.com.br;
   
           if ($host = seusubdominio.com.br) {
               return 301 https://$host$request_uri;
           } # managed by Certbot
   
           return 404; # managed by Certbot
       }
   
       server {
           listen 443 ssl;
           listen [::]:443 ssl ipv6only=on;
           server_name seusubdominio.com.br;
   
           ssl_certificate /etc/nginx/certificates/fullchain.pem; # managed by Certbot
           ssl_certificate_key /etc/nginx/certificates/privkey.pem; # managed by Certbot
           include /etc/nginx/certificates/options-ssl-nginx.conf; # managed by Certbot
           ssl_dhparam /etc/nginx/certificates/ssl-dhparams.pem; # managed by Certbot
   
           if ($host != seusubdominio.com.br) {
               return 404; # managed by Certbot
           }
           location / {
               proxy_pass http://localhost:3000;
               proxy_set_header Host $host;
               proxy_set_header X-Real-IP $remote_addr;
           }
   
           location /api/ {
               proxy_pass http://localhost:8080;
               proxy_redirect off;
               proxy_set_header Host $host;
               proxy_set_header X-Real-IP $remote_addr;
               proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
               proxy_set_header X-Forwarded-Host $host;
           }
       }
   }
   
   # Uncomment the following block to use the server without SSL
   # server {
   #     listen 80;
   #     listen [::]:80;
   #     server_name insightchallenge;
   #     if ($host != seusubdominio.com.br) {
   #             return 404; # managed by Certbot
   #      }
   #      location / {
   #           proxy_pass http://localhost:3000;
   #          proxy_set_header Host $host;
   #          proxy_set_header X-Real-IP $remote_addr;
   #      }
   # 
   #       location /api/ {
   #          proxy_pass http://localhost:8080;
   #           proxy_redirect off;
   #           proxy_set_header Host $host;
   #           proxy_set_header X-Real-IP $remote_addr;
   #           proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
   #           proxy_set_header X-Forwarded-Host $host;
   #       }
   # }
   ```

   Habilite a configuração do site: Crie um link simbólico para habilitar a configuração do site:
   ```sh
   sudo ln -s /etc/nginx/sites-available/insightchallenge /etc/nginx/sites-enabled/
   ```
## Configurando o Nginx com Docker Compose

   Para configurar o Nginx usando Docker Compose, siga os passos abaixo:

0. **Crie os arquivos de configuração:**
   - Crie os arquivos `nginx.conf`, `sites-available/insightchallenge`, e `certificates` na raiz do seu projeto. Adicione as configurações necessárias para o Nginx e os certificados SSL.
   -  Caso queira usar SSL, crie o arquivo generate_certs.sh para gerar os certificados SSL automaticamente:
    ```sh
      #!/bin/bash
      
      # Parar o NGINX para liberar a porta 80
      docker-compose down
      
      # Gerar os certificados SSL usando o Certbot
      docker run -it --rm --name certbot \
      -v "$(pwd)/certificates:/etc/letsencrypt" \
      certbot/certbot certonly --standalone -d seudominio1.br
      
      # Reiniciar o NGINX com os novos certificados
      docker-compose up -d
    ```
   - Dê permissão de execução ao script:
      ```sh
      chmod +x generate_certs.sh
      ```
     

1. **Crie o arquivo Docker Compose:**
   Adicione o seguinte conteúdo ao seu arquivo `docker-compose.yml`:
    ```yaml
    services:
      mongodb:
        image: mongo:8.0-rc-jammy
        container_name: mongodb_insight
        restart: always
        environment:
          MONGO_INITDB_ROOT_USERNAME: rootinsight
          MONGO_INITDB_ROOT_PASSWORD: rootinsight
          MONGO_USERNAME: insightuser
          MONGO_PASSWORD: insightpassword
          MONGO_INITDB_DATABASE: insight
        volumes:
          - ./backend/mongodb/mongo-init.js:/docker-entrypoint-initdb.d/mongo-init.js
        ports:
          - "0.0.0.0:27017:27017"
        networks:
          - insight-network

      mongo-express:
        image: mongo-express
        container_name: mongodb_express_insight
        environment:
          ME_CONFIG_MONGODB_SERVER: mongodb_insight
          ME_CONFIG_MONGODB_PORT: 27017
          ME_CONFIG_MONGODB_ENABLE_ADMIN: true
          ME_CONFIG_MONGODB_ADMINUSERNAME: rootinsight
          ME_CONFIG_MONGODB_ADMINPASSWORD: rootinsight
          ME_CONFIG_BASICAUTH_USERNAME: admin
          ME_CONFIG_BASICAUTH_PASSWORD: admin
        depends_on:
          - mongodb
        ports:
          - "0.0.0.0:8081:8081"
        networks:
          - insight-network

      backend:
        container_name: insight_backend
        build:
          context: ./backend
          dockerfile: Dockerfile
        environment:
          JWT_SECRET: insightsecret
          MONGODB_USERNAME: insightuser
          MONGODB_PASSWORD: insightpassword
          MONGODB_HOST: mongodb_insight
          MONGODB_PORT: 27017
          MONGODB_DATABASE: insight
          PORT: 8080
          JAR_FILE: ./target/SupplierApplicationInsight-1.0.0.jar
        ports:
          - "0.0.0.0:8080:8080"
        depends_on:
          - mongodb
        networks:
          - insight-network

      frontend:
        container_name: insight_frontend
        build:
          context: ./frontend
          dockerfile: DockerfileFront
        environment:
          REACT_APP_BACKEND_URL: http://localhost:8080
        ports:
          - "0.0.0.0:3000:3000"
        networks:
          - insight-network

      nginx:
        image: nginx:latest
        container_name: insight_nginx
        ports:
          - "80:80"
          #- "443:443" Caso queira usar SSL
        volumes:
          - ./nginx.conf:/etc/nginx/nginx_default.conf
          - ./sites-available/insightchallenge:/etc/nginx/sites-available/insightchallenge
          #- ./certificates:/etc/nginx/certificates Caso queira usar SSL
        networks:
          - insight-network

    networks:
      insight-network:
        driver: bridge
    ```

2. **Configure o Nginx:**
   Certifique-se de que os seus arquivos de configuração do Nginx estão corretamente configurados:
    - `nginx.conf` deve estar localizado na raiz do seu projeto.
    - `sites-available/insightchallenge` deve conter as suas configurações específicas do site.
    -  O diretório `certificates` deve conter os seus certificados SSL, caso você esteja usando SSL.
   Caso Não tenha feito isso, siga as instruções da [parte anterior](#configurando-nginx).

3. **Inicie os serviços:**
   Execute o seguinte comando para iniciar todos os serviços:
    ```sh
    docker-compose up
    ```

Após executar o comando acima, seu frontend estará disponível em `http://localhost:3000`, o backend em `http://localhost:8080`, e o Nginx estará configurado para servir sua aplicação.

### Documentação extra
- Arquivo de Modelagem de Dados: [Modelagem de Dados](./docs/DiagramaInsight.pdf)
