package com.fges.todoapp.logic;

import com.fges.todoapp.data.FileHandler;

import java.io.IOException;
import java.util.Arrays;

public class TodoListingLogic {
    private final FileHandler fileHandler;

    public TodoListingLogic(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public int list(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Missing file name");
            return 1;
        }

        String fileName = args[1];
        boolean showDoneOnly = Arrays.asList(args).contains("--done");

        String fileContent = fileHandler.readFileContent(fileName);

        System.out.println("Todos from file " + fileName + ":");
        String[] todos = fileContent.split("\n");
        for (String todo : todos) {
            if (showDoneOnly) {
                if (todo.startsWith("Done:")) {
                    System.out.println("- " + todo);
                }
            } else {
                System.out.println("- " + todo);
            }
        }
        return 0;
    }
}
