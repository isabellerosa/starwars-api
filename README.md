# Desafio Star Wars API

## Detalhes

- Linguagen: Java
- Banco: MongoDB

## Requisitos

[ :heavy_check_mark: ] API REST

[ :heavy_check_mark: ] Para cada planeta, os seguintes dados devem ser obtidos do banco de dados da aplicação, sendo inserido manualmente: Nome, Clima, Terreno

[ :heavy_check_mark: ] Cada planeta deve ter a quantidade de aparições em filmes, obtidas pela API pública do Star Wars

## Funcionalidades

[ :heavy_check_mark: ] Adicionar um planeta (com nome, clima e terreno)

[ :heavy_check_mark: ] Listar planetas

[ :heavy_check_mark: ] Buscar por nome

[ :heavy_check_mark: ] Buscar por ID

[ :heavy_check_mark: ] Remover planeta

## Observações

[ :heavy_check_mark:  ] Um bom software é um software bem testado.

## Extras

- Swagger
- Docker (dockerfile, docker-compose)

## Como rodar

### Utilizando o Makefile

> Rode `make` para obter todos os comandos disponiveis.

1. Faça um build da imagem

    ```bash
    make image
    ```

1. Starte a aplicação e o mongo

    ```bash
    make up
    ```

1. Acesse o endpoint através do Swagger em [/api/docs](localhost:8080/api/docs)

1. Quando terminar, pare e remova o container

    ```bash
    make clean-stop
    ```
