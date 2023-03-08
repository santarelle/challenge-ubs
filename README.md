# Desafio UBS - Parte 1

Foi criado m projeto responsável por ler e armazenar os arquivos no banco de dados.

O projeto se encontra na pasta `ubs-batch-import-data`.

## Tecnologias

- Maven
- Java 8
- Spring Boot
- Spring Batch
- Spring Data JPA
- Mockito
- JUnit
- Liquibase
- SQL Server
- Docker
- Docker Compose

## Iniciando o banco de dados

Vamos precisar de um banco de dados para armazenar os estoques. Para isso, utilizei o SQL Server rodando no Docker com Docker Compose. Abra um terminal e execute o comando abaixo:

```sh
docker-compose up -d
```

OBS: Caso o banco de dados não seja criado automaticamente, favor executar a Query abaixo no seu SQL Server Managment

```sql
CREATE DATABASE UBS;
```

## Projeto UBS-BATCH-IMPORT-DATA

Agora, vamos navegar para a pasta do projeto. No terminal, execute o comando abaixo:

```sh
cd ubs-batch-import-data/
```

## 01. Buildando a aplicação

Com o terminal aberto, e já na pasta do projeto `ubs-batch-import-data`, vamos usar o Maven para executar o build e os testes. No terminal, execute o comando abaixo:

```sh
mvn clean package install
```

## 02. Executando as migrations do banco de dados

Após o build ser executado, podemos chamar diretamente o JAR da aplicação via linha de comando. No terminal vamos chamar nosso JAR passando o profile `migration`. O Liquibase ira criar nossas tabelas no banco e dados e gerenciar as manutenções futuras. No terminal execute o comando abaixo:

```sh
java -jar -Dspring.profiles.active=migration target/ubs-batch-import-data-0.0.1.jar
```

## 03. Executando o job de importacao de dados

Agora que nossas tabelas já estão criadas, vamos executar o nosso `JOB` de importação que irá ler os arquivos e armazenar no banco de dados. Usaremos um profile diferente, chamado de `job`. No terminal, execute o comando abaixo:

```sh
java -jar -Dspring.profiles.active=job target/ubs-batch-import-data-0.0.1.jar
```

## EXTRAS - Parando o banco de dados e removendo o container docker e as tabelas

No pasta raiz do git, abra um terminal e execute o comando abaixo:

```sh
docker-compose down
```

# Desafio - Parte 2

Para este desafio obtei por criar um novo projeto chamado de `ubs-api`. Ele é responsável por calcular as vendas de um produto informado com base na quantidade de lojas.

## Pré-requisito

É Necessário que o banco de dados criado no `Desafio - Parte 1` esteja rodando, com as migrations executadas e o job também executado.

## Tecnologias

- Java 8
- Maven
- Spring Boot
- Spring Web
- Spring Data JPA
- SQL Server
- Mockito
- JUnit


## Pasta do projeto

Vamos navegar para a pasta do projeto. Com um terminal de sua preferencia digite o comando abaixo:

```sh
cd ubs-api/
```

## 01. Buildando a aplicação

Dentro da pasta do projeto vamos executar o comando abaixo para compilar, buildar e executar os testes do nosso projeto:

```sh
mvn clean package install
```

## 02. Executando o projeto

Após o projeto ser buildado na etapa anterior, vamos executar o nosso JAR através do terminal. Digite o comando abaixo:

```sh
java -jar target/ubs-api-0.0.1.jar
```

## 03. Fazendo a chamada para o Endpoint de calculo

Abra um novo terminal e execute o cURL abaixo para chamar o endpoint passando os parametros necessários (OBS: se preferir usar o Postman fique a vontade):

```sh
curl --location 'http://localhost:8080/v1/calculateSales' \
--header 'Content-Type: application/json' \
--data '{
    "product": "EMMS",
    "storeQuantity": 2
}'
```

Se tudo estiver configurado corretamente, o response será algo parecido com este:

```json
200 OK
[
    {
        "store": "Loja 1",
        "details": [
            {
                "quantity": 37,
                "price": 3.75,
                "volume": 138.75
            },
            {
                "quantity": 18,
                "price": 5.29,
                "volume": 95.22
            },
            {
                "quantity": 39,
                "price": 0.24,
                "volume": 9.36
            },
            {
                "quantity": 29,
                "price": 1.35,
                "volume": 39.15
            },
            {
                "quantity": 40,
                "price": 4.74,
                "volume": 189.60
            },
            {
                "quantity": 6,
                "price": 3.55,
                "volume": 21.30
            },
            {
                "quantity": 31,
                "price": 7.45,
                "volume": 230.95
            },
            {
                "quantity": 50,
                "price": 5.80,
                "volume": 290.00
            },
            {
                "quantity": 10,
                "price": 7.05,
                "volume": 70.50
            },
            {
                "quantity": 16,
                "price": 2.63,
                "volume": 42.08
            },
            {
                "quantity": 2,
                "price": 9.36,
                "volume": 18.72
            },
            {
                "quantity": 15,
                "price": 5.61,
                "volume": 84.15
            },
            {
                "quantity": 45,
                "price": 8.73,
                "volume": 392.85
            },
            {
                "quantity": 30,
                "price": 9.32,
                "volume": 279.60
            }
        ],
        "summary": {
            "quantity": 368,
            "financial": 1902.23,
            "averagePrice": 5.1691
        }
    },
    {
        "store": "Loja 2",
        "details": [
            {
                "quantity": 37,
                "price": 3.75,
                "volume": 138.75
            },
            {
                "quantity": 18,
                "price": 5.29,
                "volume": 95.22
            },
            {
                "quantity": 39,
                "price": 0.24,
                "volume": 9.36
            },
            {
                "quantity": 29,
                "price": 1.35,
                "volume": 39.15
            },
            {
                "quantity": 40,
                "price": 4.74,
                "volume": 189.60
            },
            {
                "quantity": 6,
                "price": 3.55,
                "volume": 21.30
            },
            {
                "quantity": 30,
                "price": 7.45,
                "volume": 223.50
            },
            {
                "quantity": 49,
                "price": 5.80,
                "volume": 284.20
            },
            {
                "quantity": 9,
                "price": 7.05,
                "volume": 63.45
            },
            {
                "quantity": 15,
                "price": 2.63,
                "volume": 39.45
            },
            {
                "quantity": 1,
                "price": 9.36,
                "volume": 9.36
            },
            {
                "quantity": 14,
                "price": 5.61,
                "volume": 78.54
            },
            {
                "quantity": 44,
                "price": 8.73,
                "volume": 384.12
            },
            {
                "quantity": 31,
                "price": 9.32,
                "volume": 288.92
            }
        ],
        "summary": {
            "quantity": 362,
            "financial": 1864.92,
            "averagePrice": 5.1517
        }
    }
]
```

## END

Qualquer dúvida estarei a disposição! ;)