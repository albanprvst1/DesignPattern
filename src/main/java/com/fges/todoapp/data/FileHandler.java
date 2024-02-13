package com.fges.todoapp.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler {
    public String readFileContent(String fileName) throws IOException {
        Path filePath = Path.of(fileName);
        return Files.readString(filePath);
    }

    public void writeToFile(String fileName, String content) throws IOException {
        Files.writeString(Path.of(fileName), content);
    }
}
