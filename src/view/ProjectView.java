package calendar_schedule.view;

import java.util.*;

public class ProjectView {

    private Scanner scanner;
    
    public ProjectView(Scanner scanner){
        this.scanner = scanner;
    }
    
    public String projectView(){
        System.out.println("\nHere are the list of commands:\n\nView Project : Views current project\nDelete Project : Deletes current project\nAdd Task : Adds a task\nEdit Task : Edit an existing task\nView Task: View an existing task");
        String input = scanner.nextLine();
        return input;
    }
    
    public Map<String, String> addProject(){
        Map<String, String> project = new HashMap<String, String>();
        System.out.println("give a project title : ");
        String input = scanner.nextLine();
        project.put("title", input);
        System.out.println("give " + input + " a description : ");
        input = scanner.nextLine();
        project.put("description", input);
        return project;
    }
    
    public String deleteProject(){
        System.out.println("title of the project to delete : ");
        return scanner.nextLine();
    }
    
    public String editProject(){
        System.out.println("title of the project to edit : ");
        return scanner.nextLine();
    }
    
    public String viewProject(){
        System.out.println("title of the project to view : ");
        return scanner.nextLine();
    }
    
    public void viewProject(String title, String description){
        System.out.println("title : " + title);
        System.out.println("description : " + description + "\n");
    }
    
    public void viewIsProjectExisting(String title, String description, boolean exist){
        if(exist){
            System.out.println("Project Exists");
            viewProject(title, description);
        } else {
            projectNotFound(title);
        }
    }
    
    public void viewIsProjectDeleted(String title, boolean removed){
        if(removed){
            System.out.println("Deleted " + title + " successfully");
        } else {
            projectNotFound(title);
        }
    }
    
    public void projectNotFound(String title){
        System.out.println(title + " not found");
    }
    
    public void noProjectsFound(){
        System.out.println("No projects found");
    }
    
}