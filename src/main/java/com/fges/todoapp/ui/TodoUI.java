package com.fges.todoapp.ui;

import com.fges.todoapp.data.FileHandler;
import com.fges.todoapp.data.FileSystemManager; 
import com.fges.todoapp.logic.TodoInsertionLogic;
import com.fges.todoapp.logic.TodoListingLogic;
import com.fges.todoapp.logic.TodoMigrationLogic;

import java.io.IOException;
import java.util.Arrays;

public class TodoUI {
    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler(new FileSystemManager()); 
        TodoInsertionLogic insertionLogic = new TodoInsertionLogic(fileHandler);
        TodoListingLogic listingLogic = new TodoListingLogic(fileHandler);
        TodoMigrationLogic migrationLogic = new TodoMigrationLogic(fileHandler);

        if (args.length == 0) {
            System.out.println("Usage: ./exec.sh <command> [options]");
            return;
        }

        String command = args[0];
        String[] arguments = Arrays.copyOfRange(args, 1, args.length);
        int statusCode;

        try {
            switch (command) {
                case "insert":
                    statusCode = insertionLogic.insert(arguments);
                    break;
                case "list":
                    statusCode = listingLogic.list(arguments);
                    break;
                case "migrate":
                    statusCode = migrationLogic.migrate(arguments);
                    break;
                default:
                    System.err.println("Unknown command: " + command);
                    return;
            }
        } catch (IllegalArgumentException | IOException e) {
            System.err.println("Error: " + e.getMessage());
            return;
        }

        System.exit(statusCode);
    }
}
