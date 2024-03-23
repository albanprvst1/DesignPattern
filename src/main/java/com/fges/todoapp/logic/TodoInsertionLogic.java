package com.fges.todoapp.logic;

import com.fges.todoapp.data.FileHandler;

import java.io.IOException;
import java.util.Arrays;

public class TodoInsertionLogic {
    private final FileHandler fileHandler;

    public TodoInsertionLogic(FileHandler fileHandler) {
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

        if (fileContent.startsWith("[")) {
            if (!fileContent.equals("[")) {
                fileContent = fileContent.substring(0, fileContent.length() - 1) + ",";
            }
            fileHandler.writeToFile(fileName, fileContent + "\"" + newTodo + "\"]");
        } else {
            fileHandler.writeToFile(fileName, "[\"" + newTodo + "\"]");
        }

        System.out.println("Todo inserted successfully into file " + fileName + ".");
        return 0;
    }
}
