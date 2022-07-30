package calendar_schedule.model;

import calendar_schedule.model.Task;
import java.time.LocalDateTime;
import java.util.List;

public class Project {
    
    private String title;
    private String description;
    private List<Task> tasks;
    
    public Project(String title, String description, List<Task> tasks) {
        this.title = title;
        this.description = description;
        this.tasks = tasks;
    }
    
    public String getTitle(){
        return title;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public List<Task> getTasks(){
        return tasks;
    }
    
    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
    }
    
}