// TodoUI.java
package com.fges.todoapp.ui;

import com.fges.todoapp.data.FileHandler; // Importer la classe FileHandler
import com.fges.todoapp.logic.TodoLogic;

public class TodoUI {
    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler(); // Créer une instance de FileHandler
        TodoLogic todoLogic = new TodoLogic(fileHandler); // Passer FileHandler à TodoLogic
        int statusCode = todoLogic.exec(args); // Exécuter la logique de l'application
        System.exit(statusCode); // Quitter l'application avec le code de statut
    }
}
