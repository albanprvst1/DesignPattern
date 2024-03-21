package com.fges.todoapp.ui;

import com.fges.todoapp.data.FileHandler;
import com.fges.todoapp.logic.TodoLogic;

import java.io.IOException;
import java.util.Arrays;

public class TodoUI {
    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        TodoLogic todoLogic = new TodoLogic(fileHandler);

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
                    statusCode = todoLogic.insert(arguments);
                    break;
                case "list":
                    statusCode = todoLogic.list(arguments);
                    break;
                case "migrate":
                    statusCode = todoLogic.migrate(arguments);
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
