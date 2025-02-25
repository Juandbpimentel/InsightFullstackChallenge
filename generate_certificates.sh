#!/bin/bash

# Parar o NGINX para liberar a porta 80
docker compose down

# Gerar os certificados SSL usando o Certbot
certbot certonly --standalone -d insightchallenge.juanpimentel.dev.br \
  --staple-ocsp -m juandbpimentel@gmail.com --agree-tos

# Cria a pasta certificates se não existir
if [ ! -d ./certificates ]; then
  mkdir ./certificates
fi

# Criar o arquivo options-ssl-nginx.conf se não existir
if [ ! -f /etc/letsencrypt/options-ssl-nginx.conf ]; then
  cat <<EOL > /etc/letsencrypt/options-ssl-nginx.conf
ssl_protocols TLSv1.2 TLSv1.3;
ssl_prefer_server_ciphers on;
ssl_ciphers "ECDHE-ECDSA-AES256-GCM-SHA384:ECDHE-RSA-AES256-GCM-SHA384:ECDHE-ECDSA-CHACHA20-POLY1305:ECDHE-RSA-CHACHA20-POLY1305:ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES128-GCM-SHA256";
ssl_session_timeout 1d;
ssl_session_cache shared:SSL:50m;
ssl_session_tickets off;
ssl_stapling on;
ssl_stapling_verify on;
EOL
fi

# Gerar o arquivo ssl-dhparams.pem se não existir
if [ ! -f /etc/letsencrypt/ssl-dhparams.pem ]; then
  openssl dhparam -out /etc/letsencrypt/ssl-dhparams.pem 2048
fi

# Copiar os certificados para a pasta certificates
cp /etc/letsencrypt/live/insightchallenge.juanpimentel.dev.br/fullchain.pem ./certificates/fullchain.pem
cp /etc/letsencrypt/live/insightchallenge.juanpimentel.dev.br/privkey.pem ./certificates/privkey.pem
cp /etc/letsencrypt/options-ssl-nginx.conf ./certificates/options-ssl-nginx.conf
cp /etc/letsencrypt/ssl-dhparams.pem ./certificates/ssl-dhparams.pem

# Reiniciar o NGINX com os novos certificados
docker compose up -d