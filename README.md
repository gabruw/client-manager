# Client Manager API

## Objetivo
Uma API desenvlvida com Spring Boot, com o intuito de gerênciar clientes e suas cidades.
Está aplicação segue as especificações do escopo que pode ser econtrado através deste link: https://github.com/recrutamento-compasso/spring-boot-interview

## Especificações Técnicas
- [X] MySQL 8
- [X] Spring Boot 2.3.5
- [X] Hibernate
- [X] EhCache
- [X] H2
- [X] Lombok
- [X] Model Mapper

## Teste
- [X] JUnit 5
- [X] Mockito
- [X] PITest

## Segurança
- [X] JWT
- [X] Spring Security
- [X] Roles

## Documentação
- [X] Swagger

## Básico

### Ambiente

Foram adicionados três ambientes:

- Developer (dev);
- Production (prod);
- Quality (test);

### Teste de Mutação

O teste de mutação pode ser executado com o comando:
```sh
mvn org.pitest:pitest-maven:mutationCoverage
```
