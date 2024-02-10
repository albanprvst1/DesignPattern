// TodoLogic.java
package com.fges.todoapp.logic;

import com.fges.todoapp.data.FileHandler;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;

public class TodoLogic {
    private final FileHandler fileHandler;

    public TodoLogic(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public int exec(String[] args) {
        TodoLogic todoLogic = new TodoLogic(new FileHandler());
        return todoLogic.execute(args);
    }

    private int execute(String[] args) {
        try {
            CommandLine cmd = parseCommandLine(args);
            String fileName = cmd.getOptionValue("s");

            List<String> positionalArgs = cmd.getArgList();
            if (positionalArgs.isEmpty()) {
                System.err.println("Missing Command");
                return 1;
            }

            String command = positionalArgs.get(0);
            String fileContent = fileHandler.readFileContent(fileName);

            if (command.equals("insert")) {
                handleInsertCommand(fileName, positionalArgs, fileContent);
            }

            if (command.equals("list")) {
                handleListCommand(fileName, fileContent);
            }

            System.err.println("Done.");
            return 0;
        } catch (ParseException | IOException ex) {
            System.err.println("Error: " + ex.getMessage());
            return 1;
        }
    }

    private CommandLine parseCommandLine(String[] args) throws ParseException {
        Options cliOptions = new Options();
        cliOptions.addRequiredOption("s", "source", true, "File containing the todos");
        CommandLineParser parser = new DefaultParser();
        return parser.parse(cliOptions, args);
    }

    private void handleInsertCommand(String fileName, List<String> positionalArgs, String fileContent) throws IOException {
        if (positionalArgs.size() < 2) {
            System.err.println("Missing TODO name");
            return;
        }
        String todo = positionalArgs.get(1);

        if (fileName.endsWith(".json")) {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode todosArray = fileContent.isEmpty() ? mapper.createArrayNode() : (ArrayNode) mapper.readTree(fileContent);
            todosArray.add(todo);
            fileHandler.writeToFile(fileName, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(todosArray));
            System.out.println("Todo inserted successfully into JSON file.");
        } else if (fileName.endsWith(".csv")) {
            String updatedContent = fileContent.isEmpty() ? todo : fileContent + "\n" + todo;
            fileHandler.writeToFile(fileName, updatedContent);
            System.out.println("Todo inserted successfully into CSV file.");
        } else {
            System.err.println("Unsupported file format: " + fileName);
        }
    }

    private void handleListCommand(String fileName, String fileContent) {
        if (fileName.endsWith(".json")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode todosArray = (ArrayNode) mapper.readTree(fileContent);
                if (todosArray.size() == 0) {
                    System.out.println("No todos found in JSON file.");
                } else {
                    System.out.println("Todos from JSON file:");
                    todosArray.forEach(node -> System.out.println("- " + node.asText()));
                }
            } catch (IOException e) {
                System.err.println("Error reading JSON file: " + e.getMessage());
            }
        } else if (fileName.endsWith(".csv")) {
            if (fileContent.isEmpty()) {
                System.out.println("No todos found in CSV file.");
            } else {
                System.out.println("Todos from CSV file:");
                String[] todos = fileContent.split("\n");
                for (String todo : todos) {
                    System.out.println("- " + todo);
                }
            }
        } else {
            System.err.println("Unsupported file format: " + fileName);
        }
    }
}
