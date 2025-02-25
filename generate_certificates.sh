#!/bin/bash

# Parar o NGINX para liberar a porta 80
docker compose down

# Gerar os certificados SSL usando o Certbot
certbot certonly --standalone -d insightchallenge.juanpimentel.dev.br \
  --staple-ocsp -m juandbpimentel@gmail.com --agree-tos

# Copiar os certificados para a pasta certificates
cp /etc/letsencrypt/live/insightchallenge.juanpimentel.dev.br/fullchain.pem ./certificates/fullchain.pem
cp /etc/letsencrypt/live/insightchallenge.juanpimentel.dev.br/privkey.pem ./certificates/privkey.pem
cp /etc/letsencrypt/options-ssl-nginx.conf ./certificates/options-ssl-nginx.conf
cp /etc/letsencrypt/ssl-dhparams.pem ./certificates/ssl-dhparams.pem

# Reiniciar o NGINX com os novos certificados
docker compose up -d