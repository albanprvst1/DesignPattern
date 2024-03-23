
# PRUVOST ALBAN
# Rapport sur l'impact des Design Patterns dans mon projet 

Le projet TodoApp met en œuvre plusieurs Design Patterns pour améliorer sa structure et sa maintenabilité. Voici un aperçu des principaux choix de Design Patterns et de leur impact sur la base de code.

## Singleton Pattern

Le Singleton Pattern est utilisé dans la classe `FileHandler` pour garantir qu'une seule instance de `FileManager` est créée et partagée dans toute l'application. Cela permet d'économiser des ressources en évitant la création multiple d'instances de `FileManager`.

## Strategy Pattern

Le Strategy Pattern est implémenté dans les classes `TodoInsertionLogic`, `TodoListingLogic` et `TodoMigrationLogic`. Chacune de ces classes utilise une stratégie différente pour effectuer ses opérations spécifiques. Par exemple, `TodoInsertionLogic` utilise une stratégie pour insérer une nouvelle tâche dans l'espace de stockage spécifié.

## Adapter Pattern

L'Adapter Pattern est utilisé pour adapter l'interface de la classe `FileManager` à celle de la classe `TodoMigrationLogic`. Cela permet à `TodoMigrationLogic` d'utiliser les fonctionnalités de `FileManager` sans modifier son code existant.

## Commentaires sur les classes et méthodes

### `FileHandler`

- `readFileContent(fileName: String): String`: Lit le contenu d'un fichier spécifié par son nom.
- `writeToFile(fileName: String, content: String): void`: Écrit le contenu dans un fichier spécifié par son nom.
- `migrateFile(sourceFileName: String, outputFileName: String): void`: Migre le contenu d'un fichier source vers un fichier de sortie.

### `TodoInsertionLogic`

- `insert(arguments: String[]): int`: Insère une nouvelle tâche dans l'espace de stockage avec les arguments spécifiés.

### `TodoListingLogic`

- `list(arguments: String[]): int`: Affiche la liste des tâches à partir de l'espace de stockage avec les arguments spécifiés.

### `TodoMigrationLogic`

- `migrate(arguments: String[]): int`: Migre les données de l'espace de stockage source vers un autre espace de stockage avec les arguments spécifiés.
