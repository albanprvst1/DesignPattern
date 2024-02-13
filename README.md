
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

## Lancer programme 
-./exec.sh insert -s test.json "toto2"   
-./exec.sh list -s test.json 

## Changements Apportés

### SEMAINE 1 : Principe de Responsabilité Unique (SRP)

Le code initial avait une seule classe (`App`) qui traitait les commandes, analysait les options de ligne de commande et interagissait avec les fichiers. Pour respecter le SRP, le code a été divisé en trois classes distinctes :

- `CommandProcessor`: Gère la logique de traitement des commandes.
- `CommandLineParser`: Gère l'analyse des options de ligne de commande.
- `FileManager`: Gère l'interaction avec les fichiers.

Cela permet d'isoler chaque responsabilité dans une classe distincte, facilitant la maintenance et l'évolutivité.

## SEMAINE 2 : 

-la méthode exec n'est pas dans le App.Java - à modifier 
-soucis fichier GhostTests.java ne marche pas car méthode exec pas dans App.java
J'ai réorganisé le code  en une architecture trois tiers en utilisant des packages distincts pour la présentation (UI), la logique (Logic), et la manipulation de données (Data). Les fichiers ont été répartis en conséquence dans les packages com.fges.todoapp.ui, com.fges.todoapp.logic, et com.fges.todoapp.data. De plus, j'ai conservé le fichier App.java dans le package principal com.fges.todoapp en tant que point d'entrée de l'application.

## SEMAINE 3 : 

-What you did and why:
    Développement application Java: Besoin fonctionnalités robustes.

-What helped you and why:
    Bonne maîtrise Java et expérience: Avantage développement.

-What did you find difficult:
    Structuration code: Difficulté clarté et maintenabilité.

-What did not help you:
    Besoin ressources spécifiques: Modélisation données, gestion erreurs.

-What did you need to change:
    Ajustement architecture: Respect principes conception logicielle.
    
-Anything relevant:
    Expérience enrichissante: Approfondissement connaissances développement.
    
## SEMAINE 4 : ADD BOUTON DONE 

- **What you did and why:** Ajout d'une fonctionnalité pour marquer les tâches comme "Done" dans une application Java. La décision était motivée par le besoin d'améliorer la gestion des tâches et de fournir une meilleure visibilité sur leur état.

- **What helped you and why:** La connaissance approfondie de Java et l'expérience préalable ont été des atouts majeurs. Cela a facilité l'ajout de la nouvelle fonctionnalité et a permis de résoudre rapidement les problèmes.

- **What did you find difficult:** La difficulté principale était liée à la structuration du code pour garantir la clarté et la maintenabilité. La gestion des états "Done" et "Not Done" a nécessité une attention particulière.

- **What did not help you:** Le manque de ressources spécifiques, comme des modèles de données bien définis, a posé des défis dans l'implémentation de la nouvelle fonctionnalité. La gestion des erreurs était également une préoccupation.

- **What did you need to change:** Des ajustements dans l'architecture ont été nécessaires pour intégrer la nouvelle fonctionnalité "Done". Cela a impliqué des modifications pour améliorer la clarté du code et faciliter la gestion des états.

- **Anything relevant:** L'expérience a été éducative, offrant une opportunité d'approfondir la compréhension du développement logiciel. Les ajustements apportés à l'architecture ont été essentiels pour résoudre les problèmes rencontrés lors de l'implémentation de la fonctionnalité "Done".
