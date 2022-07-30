package calendar_schedule.view;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskView {
    
    private Scanner scanner;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    
    public String taskView(){
        System.out.println("\nHere are the list of commands:\n\nView Task : Views current task\nDelete Task : Deletes current task\nAdd Dependent: Adds a dependent\nDelete Dependent : Deletes a dependent");
        String input = scanner.nextLine();
        return input;
    }
    
    public TaskView(Scanner scanner){
        this.scanner = scanner;
    }
    
    public Map<String, String> addTask(){
        
        Map<String, String> project = new HashMap<String, String>();
        
        System.out.println("give a task title : ");
        String input = scanner.nextLine();
        project.put("title", input);
        System.out.println("give " + input + " a description : ");
        input = scanner.nextLine();
        project.put("description", input);
        System.out.println("when does this task start (DD-MM-YYYY HH:MM:SS)? ");
        String startTime = scanner.nextLine();
        project.put("startTime", startTime);
        String endTime;
        do{
            System.out.println("when does this task end (DD-MM-YYYY HH:MM:SS)? ");
            endTime = scanner.nextLine();
            project.put("endTime", endTime);
        }while(!isEndAfterStart(startTime, endTime));
        
        return project;
    }
    
    public String deleteTask(){
        System.out.println("title of the task to delete : ");
        return scanner.nextLine();
    }
    
    public String editTask(){
        System.out.println("title of the task to edit : ");
        return scanner.nextLine();
    }
    
    public String addDependent(){
        System.out.println("title of the task to be a dependent : ");
        return scanner.nextLine();
    }
    
    public String deleteDependent(){
        System.out.println("title of the dependent to be deleted : ");
        return scanner.nextLine();
    }
    
    public void viewTask(String title, String description, LocalDateTime startTime, LocalDateTime endTime){
        System.out.println("Task : " + title + " : " + description + " : " + startTime.format(formatter) + " - " + endTime.format(formatter));
    }
    
    public void viewDependents(){
        System.out.println("List of Dependents : ");
    }
    
    public boolean isEndAfterStart(String startTime, String endTime){
        
        LocalDateTime dateTimeStart = LocalDateTime.parse(startTime, formatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(endTime, formatter);
        
        if(dateTimeEnd.isBefore(dateTimeStart)){
            System.out.println("End Time is before Start Time\nChoose Another Time");
            return false; 
        } else {
            return true;
        }
    }
    
    public void viewIsTaskExisting(String title, String description, LocalDateTime endTime, LocalDateTime startTime, boolean exist){
        if(exist){
            System.out.println("Task Exists");
            viewTask(title, description, endTime, startTime);
        } else {
            taskNotFound(title);
        }
    }
    
    public void viewIsDependentExisting(String title, String description, LocalDateTime endTime, LocalDateTime startTime, boolean exist){
        if(exist){
            System.out.println("Dependent Exists");
            viewTask(title, description, endTime, startTime);
        } else {
            taskNotFound(title);
        }
    }
    
    public void viewIsTaskDeleted(String title, boolean removed){
        if(removed){
            System.out.println("Deleted " + title + " successfully");
        } else {
            taskNotFound(title);
        }
    }
    
    public void taskNotFound(String title){
        System.out.println(title + " not found");
    }
    
    public void noTasksFound(){
        System.out.println("No projects found");
    }
    
}