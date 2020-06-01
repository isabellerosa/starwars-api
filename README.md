![Java CI with Maven](https://github.com/isabellerosa/starwars-api/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master&event=push)

# Desafio Star Wars API

## Detalhes

- Linguagen: Java 11
- Banco: MongoDB

## Requisitos do desafio

[ :heavy_check_mark: ] API REST

[ :heavy_check_mark: ] Para cada planeta, os seguintes dados devem ser obtidos do banco de dados da aplicação, sendo inserido manualmente: Nome, Clima, Terreno

[ :heavy_check_mark: ] Cada planeta deve ter a quantidade de aparições em filmes, obtidas pela API pública do Star Wars

## Rodando os testes

> É necessario que você tenha o Docker instalado para seguir os passos abaixo.

1. Faça um build da imagem

    ```bash
    make image
    ```

1. Starte a aplicação e o Mongo

    ```bash
    make up
    ```
    > Atenção: A porta padrão é `8080`. [Instruções para alterar](#alterando-configurações)
1. Acesse os endpoints através do Swagger em [/api/docs](localhost:8080/api/docs)

1. Quando terminar, pare e remova o container

    ```bash
    make stop-clean
    ```

## Rodando os testes

- `make test`(Linux) ou `make win-test`(Windows) para rodar todos os testes

- `make unit`(Linux) ou `make win-unit`(Windows) para rodar apenas os testes de unidade

- `make e2e`(Linux) ou `make win-test`(Windows) para rodar apenas os testes end-to-end
    > Nota: Verifique as configurações do endpoint em `src/tests/resources/e2e.properties` e adapte conforme necessário

## Alterando configurações
As configurações podem ser alteradas no arquivo `.env`, localizado na raiz do projeto. 
Caso tenha alterado configurações da API e queira rodar os testes end-to-end, altere as configurações em `src/test/resources/e2e.properties`

## Observações

[ :heavy_check_mark:  ] Um bom software é um software bem testado.
