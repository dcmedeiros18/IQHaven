#!/bin/bash

mkdir -p certs
cd certs

# Gera chave privada da CA
openssl genrsa -out ca.key 2048

# Gera certificado da CA
openssl req -x509 -new -nodes -key ca.key -sha256 -days 365 -out ca.crt \
  -subj "/C=BR/ST=SP/L=SaoPaulo/O=IQHaven/CN=iqhaven-ca"

# Gera chave privada do servidor
openssl genrsa -out server.key 2048

# Gera CSR (Certificate Signing Request)
openssl req -new -key server.key -out server.csr \
  -subj "/C=BR/ST=SP/L=SaoPaulo/O=IQHaven/CN=localhost"

# Assina o certificado do servidor com a CA
openssl x509 -req -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial \
  -out server.crt -days 365 -sha256

echo ""
echo "✅ Certificados criados com sucesso em ./certs/"
ls -l
