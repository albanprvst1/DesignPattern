// FileManager.java
package com.fges.todoapp.data;

import java.io.IOException;

public interface FileManager {
    String readFileContent(String fileName) throws IOException;
    void writeToFile(String fileName, String content) throws IOException;
    void migrateFile(String sourceFileName, String outputFileName) throws IOException;
}
