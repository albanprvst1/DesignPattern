package com.fges.todoapp.logic;

import com.fges.todoapp.data.FileHandler;

import java.io.IOException;

public class TodoMigrationLogic {
    private final FileHandler fileHandler;

    public TodoMigrationLogic(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
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
