
## Commands:
### Compile with maven
```bash
mvn compile
```

### Run project jar
```bash
java -jar target/todoapp-*.jar
```

## Links
Simple CLI
- mvn https://mvnrepository.com/artifact/commons-cli/commons-cli/1.6.0
- Apache Doc https://commons.apache.org/proper/commons-cli/usage.html

Jackson
- https://www.baeldung.com/jackson-json-to-jsonnode
- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind

## Changements Apportés

### SEMAINE 1 : Principe de Responsabilité Unique (SRP)

Le code initial avait une seule classe (`App`) qui traitait les commandes, analysait les options de ligne de commande et interagissait avec les fichiers. Pour respecter le SRP, le code a été divisé en trois classes distinctes :

- `CommandProcessor`: Gère la logique de traitement des commandes.
- `CommandLineParser`: Gère l'analyse des options de ligne de commande.
- `FileManager`: Gère l'interaction avec les fichiers.

Cela permet d'isoler chaque responsabilité dans une classe distincte, facilitant la maintenance et l'évolutivité.