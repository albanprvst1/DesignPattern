package com.fges.todoapp.ui;

import com.fges.todoapp.data.FileHandler;
import com.fges.todoapp.logic.TodoLogic;

public class TodoUI {
    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        TodoLogic todoLogic = new TodoLogic(fileHandler);
        int statusCode = todoLogic.exec(args);
        System.exit(statusCode);
    }
}
