package com.fges.todoapp.logic;

import com.fges.todoapp.data.FileHandler;

import java.io.IOException;
import java.util.Arrays;

public class TodoLogic {
    private final FileHandler fileHandler;

    public TodoLogic(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public int insert(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("Missing file name or todo name");
            return 1;
        }

        String fileName = args[1];
        String todoName = args[2];
        boolean markDone = Arrays.asList(args).contains("--done");

        String fileContent = fileHandler.readFileContent(fileName);
        String newTodo = markDone ? "Done: " + todoName : todoName;

        // Vérification du format JSON et ajout du nouvel élément
        if (fileContent.startsWith("[")) {
            // Le fichier commence par '[' donc c'est une liste JSON
            // Supprimer le dernier ']' de la liste et ajouter la virgule si la liste n'est pas vide
            if (!fileContent.equals("[")) {
                fileContent = fileContent.substring(0, fileContent.length() - 1) + ",";
            }
            fileHandler.writeToFile(fileName, fileContent + "\"" + newTodo + "\"]");
        } else {
            // Le fichier ne commence pas par '[' donc ce n'est pas une liste JSON valide
            // On crée une nouvelle liste JSON avec le nouvel élément
            fileHandler.writeToFile(fileName, "[\"" + newTodo + "\"]");
        }
        
        System.out.println("Todo inserted successfully into file " + fileName + ".");
        return 0;
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

    public int migrate(String[] args) throws IOException {
        if (args.length < 4 || !args[2].equals("--output")) {
            System.err.println("Missing source or output file names for migration");
            return 1;
        }

        String sourceFileName = args[1];
        String outputFileName = args[3];

        String fileContent = fileHandler.readFileContent(sourceFileName);
        fileHandler.writeToFile(outputFileName, fileContent);
        
        System.out.println("Migration from " + sourceFileName + " to " + outputFileName + " completed successfully.");
        return 0;
    }
}
