## Desafio idwall

Serviços desenvolvidos utilizando:

- Java 17
- Spring
- Keycloak
- Discovery Server (Eureka), Api Gateway, LoadBalancer
- Docker

## Instruções de uso da aplicação:

1. Importe as collection que se encontra nos serviço (msgateway) no Postman.
2. Subir o container do Keycloak:
  - `docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.5 start-dev`
  - Importar o Realm na pasta Keycloak no (msgateway):
  - ![Screenshot_43](https://github.com/user-attachments/assets/b36e95a6-40ba-4691-8293-531ac1e80b57)

3. Subir o eureka server, depois o msapi, e depois o msgateway.

Desenho da aplicação:

![Screenshot_41](https://github.com/user-attachments/assets/51fdc06f-1f44-4c0f-b2d1-d9a02324a455)

A ideia com o novo desenho é que as requisições WEB cheguei no gateway e não diretamente na API. Assim com o Discovery Server (Eureka), conseguimos utilizar uma tecnologia chamado LoadBalancer. Com isso, podemos subir várias instancias da API e utilziar o LoadBalancer para balancear corretamente as requisições para diferentes instancias da API. Nisso, temos uma alta disponibilidade da nossa aplicação com um baixo tempo de resposta.

[Desafio Técnico_Java.pdf](https://github.com/user-attachments/files/18223135/Desafio.Tecnico_Java.pdf)

Responsa a alguns questionamentos do desafio:
![Screenshot_42](https://github.com/user-attachments/assets/d0d2799d-d8a9-4589-99dd-9f11e0e23edd)
