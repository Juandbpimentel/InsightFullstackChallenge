#!/bin/bash

# Parar o NGINX para liberar a porta 80
docker compose down

# Gerar os certificados SSL usando o Certbot
docker run -it --rm --name certbot \
  -v "$(pwd)/certificates:/etc/letsencrypt" \
  certbot/certbot certonly --standalone -d insightchallenge.juanpimentel.dev.br \
  --staple-ocsp -m juandbpimentel@gmail.com --agree-tos

# Reiniciar o NGINX com os novos certificados
docker compose up -d