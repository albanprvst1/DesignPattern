package com.fges.todoapp;

import com.fges.todoapp.logic.TodoLogic; 

public class App {

    public static void main(String[] args) throws Exception {
        System.exit(TodoLogic.exec(args));
    }

}
