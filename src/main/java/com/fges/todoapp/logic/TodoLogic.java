package com.fges.todoapp.logic;

import com.fges.todoapp.data.FileHandler;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;


public class TodoLogic {

    private static class CommandLineHandler {
        public static CommandLine parseCommandLine(String[] args) throws ParseException {
            Options cliOptions = new Options();
            cliOptions.addRequiredOption("s", "source", true, "File containing the todos");
            CommandLineParser parser = new DefaultParser();
            return parser.parse(cliOptions, args);
        }
    }

    private static class FileHandler {
        public static String readFileContent(String fileName) throws IOException {
            Path filePath = Paths.get(fileName);
            return Files.exists(filePath) ? Files.readString(filePath) : "";
        }

        public static void writeToFile(String fileName, String content) throws IOException {
            Files.writeString(Paths.get(fileName), content);
        }
    }

    public static int exec(String[] args) {
        try {
            CommandLine cmd = CommandLineHandler.parseCommandLine(args);
            String fileName = cmd.getOptionValue("s");

            List<String> positionalArgs = cmd.getArgList();
            if (positionalArgs.isEmpty()) {
                System.err.println("Missing Command");
                return 1;
            }

            String command = positionalArgs.get(0);
            String fileContent = FileHandler.readFileContent(fileName);

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

    private static void handleInsertCommand(String fileName, List<String> positionalArgs, String fileContent) throws IOException {
        if (positionalArgs.size() < 2) {
            System.err.println("Missing TODO name");
            System.exit(1);
        }
        String todo = positionalArgs.get(1);

        if (fileName.endsWith(".json")) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(fileContent);
            if (actualObj instanceof MissingNode) {
                actualObj = JsonNodeFactory.instance.arrayNode();
            }

            if (actualObj instanceof ArrayNode arrayNode) {
                arrayNode.add(todo);
            }

            FileHandler.writeToFile(fileName, actualObj.toString());
        }

        if (fileName.endsWith(".csv")) {
            if (!fileContent.endsWith("\n") && !fileContent.isEmpty()) {
                fileContent += "\n";
            }
            fileContent += todo;

            FileHandler.writeToFile(fileName, fileContent);
        }
    }

    private static void handleListCommand(String fileName, String fileContent) {
        try {
            if (fileName.endsWith(".json")) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode actualObj = mapper.readTree(fileContent);
                if (actualObj instanceof MissingNode) {
                    actualObj = JsonNodeFactory.instance.arrayNode();
                }

                if (actualObj instanceof ArrayNode arrayNode) {
                    arrayNode.forEach(node -> System.out.println("- " + node.toString()));
                }
            }

            if (fileName.endsWith(".csv")) {
                System.out.println(Arrays.stream(fileContent.split("\n"))
                        .map(todo -> "- " + todo)
                        .collect(Collectors.joining("\n"))
                );
            }
        } catch (IOException e) {
            System.err.println("Error while processing file: " + e.getMessage());
        }
    }
}