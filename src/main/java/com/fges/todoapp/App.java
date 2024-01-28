package com.fges.todoapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Nouvelle classe pour la gestion des commandes
class CommandProcessor {
    public static int process(String command, String fileName, List<String> positionalArgs, String fileContent) throws JsonMappingException, JsonProcessingException {
        // Logique de traitement des commandes
        if (command.equals("insert")) {
            // Logique d'insertion des tâches
            if (positionalArgs.size() < 2) {
                System.err.println("Missing TODO name");
                return 1;
            }
            String todo = positionalArgs.get(1);

            if (fileName.endsWith(".json")) {
                // Traitement pour les fichiers JSON
                ObjectMapper mapper = new ObjectMapper();
                JsonNode actualObj = mapper.readTree(fileContent);
                if (actualObj instanceof MissingNode) {
                    actualObj = JsonNodeFactory.instance.arrayNode();
                }

                if (actualObj instanceof ArrayNode arrayNode) {
                    arrayNode.add(todo);
                }

                try {
                    Files.writeString(Paths.get(fileName), actualObj.toString());
                } catch (IOException e) {
                    System.err.println("Error writing to file: " + e.getMessage());
                    return 1;
                }
            }
            if (fileName.endsWith(".csv")) {
                // Traitement pour les fichiers CSV
                if (!fileContent.endsWith("\n") && !fileContent.isEmpty()) {
                    fileContent += "\n";
                }
                fileContent += todo;

                try {
                    Files.writeString(Paths.get(fileName), fileContent);
                } catch (IOException e) {
                    System.err.println("Error writing to file: " + e.getMessage());
                    return 1;
                }
            }
        }

        if (command.equals("list")) {
            // Logique d'affichage des tâches
            if (fileName.endsWith(".json")) {
                // Traitement pour les fichiers JSON
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
                // Traitement pour les fichiers CSV
                System.out.println(Arrays.stream(fileContent.split("\n"))
                        .map(todo -> "- " + todo)
                        .collect(Collectors.joining("\n"))
                );
            }
        }

        System.err.println("Done.");
        return 0;
    }
}

// Nouvelle classe pour la gestion des options de ligne de commande
class CommandLineParser {
    public static CommandLine parse(String[] args) {
        Options cliOptions = new Options();
        cliOptions.addRequiredOption("s", "source", true, "File containing the todos");

        DefaultParser parser = new DefaultParser();
        try {
            return parser.parse(cliOptions, args);
        } catch (ParseException ex) {
            System.err.println("Fail to parse arguments: " + ex.getMessage());
            System.exit(1);
            return null;
        }
    }
}

// Nouvelle classe pour l'interaction avec les fichiers
class FileManager {
    public static String readFileContent(String filePath) {
        Path path = Paths.get(filePath);
        try {
            return Files.exists(path) ? Files.readString(path) : "";
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    public static void writeFileContent(String filePath, String content) {
        try {
            Files.writeString(Paths.get(filePath), content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            System.exit(1);
        }
    }
}

// Classe principale App
public class App {
    public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
        System.exit(exec(args));
    }

    public static int exec(String[] args) throws JsonMappingException, JsonProcessingException {
        CommandLine cmd = CommandLineParser.parse(args);
        String fileName = cmd.getOptionValue("s");
        List<String> positionalArgs = cmd.getArgList();
        String fileContent = FileManager.readFileContent(fileName);

        String command = positionalArgs.isEmpty() ? "" : positionalArgs.get(0);

        // Appel de la méthode de traitement des commandes
        return CommandProcessor.process(command, fileName, positionalArgs, fileContent);
    }
}
