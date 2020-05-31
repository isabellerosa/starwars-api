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

1. Acesse os endpoints através do Swagger em [/api/docs](localhost:8080/api/docs)

1. Quando terminar, pare e remova o container

    ```bash
    make clean-stop
    ```

## Rodando os testes

- `make tests` roda todos os testes

- `make unit` roda apenas os testes de unidade

- `make e2e` roda apenas os testes end-to-end
    > Nota: Verifique as configurações do endpoint em `src/tests/resources/e2e.properties` e adapte conforme necessário

## Observações

[ :heavy_check_mark:  ] Um bom software é um software bem testado.
