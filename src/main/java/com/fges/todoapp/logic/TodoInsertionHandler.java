package com.fges.todoapp.logic;

import com.fges.todoapp.data.FileHandler;

public class TodoInsertionHandler {
    private final FileHandler fileHandler;

    public TodoInsertionHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public int handleInsertion(String[] args) {
        if (args.length < 3) {
            System.err.println("Missing arguments. Usage: ./exec.sh insert -s <source> [-d] <todo name>");
            return 1; // Status code 1: Erreur d'argument
        }

        String source = null;
        String todoName = null;
        boolean isDone = false;

        // Analyser les arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-s") && i + 1 < args.length) {
                source = args[i + 1];
                i++;
            } else if (args[i].equals("-d")) {
                isDone = true;
            } else {
                todoName = args[i];
            }
        }

        if (source == null || todoName == null) {
            System.err.println("Missing arguments. Usage: ./exec.sh insert -s <source> [-d] <todo name>");
            return 1; // Status code 1: Erreur d'argument
        }

        try {
            fileHandler.insertTodo(source, todoName, isDone);
            return 0; // Status code 0: Succès
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 1; // Status code 1: Erreur
        }
    }
}
