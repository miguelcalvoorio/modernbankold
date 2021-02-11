# Modern Bank - Architecture Core starter lib

## Architecture core lib

### 1. Library Content

- ApiError models for REST endpoints response
- Common Exceptions clases for CRUD operations
- Common Exception handler for controller REST endpoints operations (autoconfigures with ```@RestControllerAdvice``` annotation)
- Error messages service with localization

### 2. Installation requirements

- To be used by other Modern Bank domains the architecture library must be available on a local repository. Gradle build file has been configured to publish it on your local maven repository
- To publish the library just execute: ```gradle clean build publishToMavenLocal```

### 3. Configuration

Some of architecture lib configuration are managed through properties files on consumer applications

- ApiExceptionHandler activation ```modernbank.apiExceptionHandler.enabled = true```
- Message properties on files named ```error.messages.properties``` plus localized versions (i.e. ```error.messages_es.properties```)

### 4. Architecture project

Things to take into account:

1. ```src/main/resources/META-INF``` includes the file ```spring.factories``` where we identify the autoconfiguration class.
