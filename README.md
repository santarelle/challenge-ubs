# Desafio UBS - Parte 1

Foi criado m projeto responsável por ler e armazenar os arquivos no banco de dados.

O projeto se encontra na pasta `ubs-batch-import-data`.

## Tecnologias

- Maven
- Java 8
- Spring Boot
- Spring Batch
- Spring Data JPA
- SQL Server
- Docker
- Docker Compose

## Iniciando o banco de dados

Vamos precisar de um banco de dados para armazenar os estoques. Para isso, utilizei o SQL Server rodando no Docker com Docker Compose. Abra um terminal e execute o comando abaixo:

```sh
docker-compose up -d
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
