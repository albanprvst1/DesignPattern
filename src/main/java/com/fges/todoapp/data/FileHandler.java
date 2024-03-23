package com.fges.todoapp.data;

import java.io.IOException;

public class FileHandler {
    private final FileManager fileManager;

    public FileHandler(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public String readFileContent(String fileName) throws IOException {
        return fileManager.readFileContent(fileName);
    }

    public void writeToFile(String fileName, String content) throws IOException {
        fileManager.writeToFile(fileName, content);
    }

    public void migrateFile(String sourceFileName, String outputFileName) throws IOException {
        fileManager.migrateFile(sourceFileName, outputFileName);
    }

    public void insertTodo(String source, String todoName, boolean isDone) throws IOException {
        throw new UnsupportedOperationException("Method 'insertTodo' not yet implemented");
    }
}
