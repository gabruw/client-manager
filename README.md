
# Client Manager API

## Objetivo
Uma API desenvolvida com Spring Boot, com o intuito de gerênciar clientes e suas cidades.
Está aplicação segue as especificações do escopo que pode ser econtrado através deste link: https://github.com/recrutamento-compasso/spring-boot-interview

## Especificações Técnicas
### Tecnologia
- [X] MySQL 8
- [X] Spring Boot 2.3.5
- [X] Hibernate
- [X] EhCache
- [X] H2
- [X] Lombok
- [X] Model Mapper

### Teste
- [X] JUnit 5
- [X] Mockito
- [X] PITest

### Segurança
- [X] JWT
- [X] Spring Security
- [X] Roles

### Documentação
- [X] Swagger
- [X] Postman

## Básico

### Ambientes

Foram adicionados três ambientes distintos:

- Desenvolvimento (dev) - **[Padrão]**
- Produção (prod)
- Teste (test)

O ambiente de desenvolvimento e produção utilizam o banco relacional **"MySQL 8"**.
O ambiente de testes utiliza o banco relacional em memória **"H2"**. 

### Execução

Para executar a aplicação, pode-se considerar três cenários:

- Execução com a **IDE (Eclipse)**
- Execução com **Jenkins (CI/CD - Tomcat 8.5)**
- Execução com **.jar**

#### Base de dados

Escolhido o ambiente, é necessário alterarmos o arquivo **"application.resourses"** relativo ao ambiente escolhido. Para que a base de dados seja gerada,  é necessário alterar a instrução  **"spring.jpa.hibernate.ddl-auto"** de **none** para **create**.

#### IDE - Eclipse
Para executar a aplicação com a IDE Eclipse, será necessário o Sprint Tools Suite (STS). Após a sua instalação, basta seguir os seguintes passos:

 1. Clique no menu "File"
 2. Clique em "Open projects from File System"
 3. Clique em "Directory" e navegue até a pasta do projeto
 4. Selecione a pasta e após, clique em "Finish"
 5. No "Project Explorer" clique bom o botão direito sobre o projeto
 6. Selecione a opção "Run As" e logo em seguida a opção "Spring Boot App"
 7. Aguarde o carregamento

#### Jenkins - CI/CD | Tomcat 8.5
##### Guias de instalação
Guia de instalação do Docker:
```
https://www.docker.com/get-started
```

Guia de instalação do Maven:
```
https://maven.apache.org/install.html
```

Guia de instalação do Jenkins:
```
https://www.jenkins.io/doc/book/installing/
```

Guia de instalação do Tomcat 8.5 (porta 8080):
```
https://tomcat.apache.org/tomcat-8.5-doc/index.html
```

Guia de integração com Jenkins e SonarQube:
```
https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-jenkins/
```

##### Execução
Para executar a aplicação com o Jenkins, será necessário primeiramente executar o **"docker-compose.yml"**, que contem uma imagem do "SonarQube" e uma do "Postgres" (servirá como base para o SonarQube). 
O arquivo pode ser encontrado na pasta:
```
./resources/documents
```

Após, execute o Jenkins e crie um novo projeto do tipo **Pipeline**. 
Dentro do projeto, no menu lateral esquerdo, clique em "Configurar". Após procure o tópico **"Pipeline"** e no campo **"Definition"**, selecione a opção **"Pipeline script from SCM"** e preencha as informações requisitadas.
No campo **"Script Path"**, preencha o valor com **".jenkinsfile"**.

Após, retorne ao menu principal do projeto e execute o "Pipeline".
Ao fim do carregamento de todas as etapas, o sistema estará disponível através do endereço:
```
<URL do Tomcat 8.5>/client-manager
```

#### JAR
Para executar a aplicação através de um arquivo **.jar**, será necessário alterar o arquivo **pom.xml** na pasta principal do projeto.  Já no arquivo faça a seguinte alteração na linha 15:

De:
```
<packaging>war</packaging>
```
Para:
```
<packaging>jar</packaging>
```
Salve e feche o arquivo. 
Após, abra um terminal na pasta principal do projeto e execute o comando:
```
mvn clean package
```

Uma pasta com o nome **"target"** será criada. Dentro desta pasta, abra um novo terminal e execute o comando:
```
java -jar <nome do arquivo .jar>
``` 

### Consultas

Além da documentação com Swagger, uma versão para o Postman pode ser encontrada através do arquivo **"postman_collection.json"** na pasta do projeto ou através do endereço abaixo:
```
https://www.postman.com/collections/3c1f6bd6b7caa866fed1
```

### Teste de Mutação

O teste de mutação pode ser executado com o comando:
```
mvn org.pitest:pitest-maven:mutationCoverage
```
Após, navegue até a pasta do PITest para visualizar os dados gerados:
```
./target/pit-reports
```
Na pasta, selecione o ultimo teste gerado e abra o arquivo **"index.html"**.
