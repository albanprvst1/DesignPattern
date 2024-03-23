// FileSystemManager.java
package com.fges.todoapp.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemManager implements FileManager {
    @Override
    public String readFileContent(String fileName) throws IOException {
        Path filePath = Path.of(fileName);
        return Files.readString(filePath);
    }

    @Override
    public void writeToFile(String fileName, String content) throws IOException {
        Files.writeString(Path.of(fileName), content);
    }

    @Override
    public void migrateFile(String sourceFileName, String outputFileName) throws IOException {
        String fileContent = readFileContent(sourceFileName);
        writeToFile(outputFileName, fileContent);
        System.out.println("Migration from " + sourceFileName + " to " + outputFileName + " completed successfully.");
    }
}
