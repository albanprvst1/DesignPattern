package com.fges.todoapp.ui;

import com.fges.todoapp.logic.TodoLogic; // Ajout de l'import

public class TodoUI {
    public static void main(String[] args) throws Exception {
        System.exit(TodoLogic.exec(args));
    }
}
