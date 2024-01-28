## Changements Apportés

### SEMAINE 1 : Principe de Responsabilité Unique (SRP)

Le code initial avait une seule classe (`App`) qui traitait les commandes, analysait les options de ligne de commande et interagissait avec les fichiers. Pour respecter le SRP, le code a été divisé en trois classes distinctes :

- `CommandProcessor`: Gère la logique de traitement des commandes.
- `CommandLineParser`: Gère l'analyse des options de ligne de commande.
- `FileManager`: Gère l'interaction avec les fichiers.

Cela permet d'isoler chaque responsabilité dans une classe distincte, facilitant la maintenance et l'évolutivité.
