# Modernbank

Personal project for self-learning about modern technologies using a banking platform as use case. Somo of those modern technologies include:

- API driven platforms
- Microservice architectures
- Event oriented and data streaming architectures
- Security architecture
- Non-sql databases
- Technical & functional monitoring
- Containers
- Serverless
- DevOps
- Others

## 1. My development environment

Some thoughs about how I configured my development environment

### 1.1. Workstation

Currently using a Mac OSx (Catalina) based laptop, but currently migrating (personal circunstances) to a Mac Mini (Big Sur) where I will work using remote desktop.

### 1.2. Desktop software base

- Visual Studio (<https://code.visualstudio.com/>)
- Docker desktop (<https://www.docker.com/products/docker-desktop>)
- Postman (<https://www.postman.com/downloads/>)
- iTerm, with zsh shell (<https://iterm2.com/>)
- HomeBrew (<https://brew.sh/index_es>)
- Git (<https://github.com/>)
- Gradle (<https://gradle.org/>)

### 1.3 Other tools

- JWT introspection (<https://jwt.io/>)

## 2. Modernbank architecture

- Spring Boot v2.4.2 (<https://spring.io/>)
- MongoDB, community version (<https://www.mongodb.com/try/download/community>)
- Kafka Confluent, community version (<https://www.confluent.io/download/>)
- Keycloak (<https://www.keycloak.org/>)
- Consul (<https://www.consul.io/>)

## 3. How to test

### Keycloak configuration and restrictions

Keycloak is the Authorization Server used in this project example.
There are two main applications defined:

1. internetFrontEnd, used as an example of a customer self-service touchpoint (i.e. Internet Home Banking applications, Mobile app, etc.). Application scopes covers both clients and employees roles.
2. tellerFrontEnd, used as an example of a employee desktop application for customer face to face interactions. Application scope only supports employee roles.

In addition to this there are two users defined:

1. John Doe, an employee of Modernbank that is also an customer
2. Jane Doe, a customer of Moderbank

There is also a third scope for Moderbank: user. It will be used for those that still are not clients (meaning they do not have any product with Modernbank), but has been authenticated as we offer some free services (i.e. adivisor).

#### Authentication restrictions

Current version of Keycloak is pretty restrictive during the token validation and authorization process. It actually checks if token issuer (iss field in JWT) is same hostname as the one used to validate it (in each of the microservices). In case both (issuer and backend microservices) run on different host servers (i.e. issuer running in localhost and microservices running in docker container), the token validation will fail.
I found three different workarounds for this:

1. Change Host header parameter when requesting the token to match with the same hostname and port than used by the microservice in the container.
2. Change Real front end URL through Keycloak admin console.
3. Add a new entry on my local hosts file matching docker container name (keycloak) with localhost address (127.0.0.1) and ensure container port mapping does not change port number, i.e. 8080:8080 (that's the one I use).
