package calendar_schedule.view;

import java.util.*;

public class MainView {
    
    private Scanner scanner;
    
    public MainView(Scanner scanner){
        this.scanner = scanner;
    }
    
    public void welcomeView(){
        System.out.println("Hello, Welcome to Calendar Scheduler");
    }

    public String entryView(){
        System.out.println("\nHere are the list of commands:\n\nView Projects : View all projects\nAdd Project : Add a new project\nDelete Project : Delete an existing project\nEdit Project : Edit an existing project\nView Project: View an existing project");
        String input = scanner.nextLine();
        return input;
    }
    
}