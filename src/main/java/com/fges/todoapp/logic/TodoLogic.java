package com.fges.todoapp.logic;

import com.fges.todoapp.data.FileHandler;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class TodoLogic {
    private final FileHandler fileHandler;

    public TodoLogic(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public int exec(String[] args) {
        return execute(args);
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
                handleInsertCommand(fileName, positionalArgs, fileContent, cmd.hasOption("d"));
            }

            if (command.equals("list")) {
                handleListCommand(fileName, fileContent, cmd.hasOption("done"));
            }

            System.err.println("Done :i am Done");
            return 0;
        } catch (ParseException | IOException ex) {
            System.err.println("Error: " + ex.getMessage());
            return 1;
        }
    }

    private CommandLine parseCommandLine(String[] args) throws ParseException {
        Options cliOptions = new Options();
        cliOptions.addRequiredOption("s", "source", true, "File containing the todos");
        cliOptions.addOption("d", "done", false, "Mark the todo as done");
        cliOptions.addOption("done", false, "List only done todos");
        CommandLineParser parser = new DefaultParser();
        return parser.parse(cliOptions, args);
    }

    private void handleInsertCommand(String fileName, List<String> positionalArgs, String fileContent, boolean markDone) throws IOException {
        if (positionalArgs.size() < 2) {
            System.err.println("Missing TODO name");
            return;
        }
        String todo = positionalArgs.get(1);

        if (fileName.endsWith(".json")) {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode todosArray = fileContent.isEmpty() ? mapper.createArrayNode() : (ArrayNode) mapper.readTree(fileContent);
            if (markDone) {
                todosArray.add(mapper.createObjectNode().put("name", todo).put("done", true));
            } else {
                todosArray.add(mapper.createObjectNode().put("name", todo));
            }
            fileHandler.writeToFile(fileName, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(todosArray));
            System.out.println("Todo inserted successfully into JSON file.");
        } else if (fileName.endsWith(".csv")) {
            String updatedContent;
            if (markDone) {
                updatedContent = fileContent + "\n\"" + todo + "\",\"Done\"";
            } else {
                updatedContent = fileContent + "\n\"" + todo + "\",\"\"";
            }
            fileHandler.writeToFile(fileName, updatedContent);
            System.out.println("Todo inserted successfully into CSV file.");
        } else {
            System.err.println("Unsupported file format: " + fileName);
        }
    }

    private void handleListCommand(String fileName, String fileContent, boolean showDoneOnly) {
        if (fileName.endsWith(".json")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode todosArray = (ArrayNode) mapper.readTree(fileContent);
                if (todosArray.size() == 0) {
                    System.out.println("No todos found in JSON file.");
                } else {
                    System.out.println("Todos from JSON file:");
                    boolean foundDoneTodo = false;
                    for (JsonNode node : todosArray) {
                        boolean isDone = node.has("done") && node.get("done").asBoolean();
                        String todoName = node.has("name") ? node.get("name").asText() : "[Missing Name]";
                        if (showDoneOnly && isDone) {
                            System.out.println("- " + todoName + " (Done)");
                            foundDoneTodo = true;
                        } else if (!showDoneOnly) {
                            System.out.println("- " + todoName + (isDone ? " (Done)" : ""));
                            if (isDone) {
                                foundDoneTodo = true;
                            }
                        }
                    }
                    if (!foundDoneTodo && showDoneOnly) {
                        System.out.println("No done todos found.");
                    } else if (!foundDoneTodo && !showDoneOnly) {
                        System.out.println("I am not done.");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading JSON file: " + e.getMessage());
            }
        } else if (fileName.endsWith(".csv")) {
            // Similar modification for CSV if needed
            // CSV format: "Todo Name","Done"
            if (fileContent.isEmpty()) {
                System.out.println("No todos found in CSV file.");
            } else {
                System.out.println("Todos from CSV file:");
                String[] lines = fileContent.split("\n");
                boolean foundDoneTodo = false;
                for (String line : lines) {
                    String[] values = line.split(",");
                    if (values.length == 2) {
                        String todoName = values[0].replace("\"", "");
                        boolean isDone = values[1].replace("\"", "").equalsIgnoreCase("Done");
                        if (showDoneOnly && isDone) {
                            System.out.println("- " + todoName + " (Done)");
                            foundDoneTodo = true;
                        } else if (!showDoneOnly) {
                            System.out.println("- " + todoName + (isDone ? " (Done)" : ""));
                            if (isDone) {
                                foundDoneTodo = true;
                            }
                        }
                    }
                }
                if (!foundDoneTodo && showDoneOnly) {
                    System.out.println("No done todos found.");
                } else if (!foundDoneTodo && !showDoneOnly) {
                    System.out.println("I am not done.");
                }
            }
        } else {
            System.err.println("Unsupported file format: " + fileName);
        }
    }

}
