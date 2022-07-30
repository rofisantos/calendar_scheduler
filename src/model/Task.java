package calendar_schedule.model;

import java.time.LocalDateTime;
import java.util.List;

public class Task implements Comparable<Task>{
    
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Task> dependents;
    
    public Task(String title, String description, LocalDateTime startTime, LocalDateTime endTime, List<Task> dependents) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dependents = dependents;
    }
    
    @Override
    public int compareTo(Task task){
        if(startTime.equals(task.getStartTime())){
            if(endTime.equals(task.getEndTime())){
                return 0;
            } else if(endTime.isBefore(task.getEndTime())){
                return -1;
            } else {
                return 1;
            }
        } else if(startTime.isBefore(task.getStartTime())){
            return -1;
        } else {
            return 1;
        }
        
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
    
    public LocalDateTime getStartTime(){
        return startTime;
    }
    
    public void setId(LocalDateTime startTime){
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime(){
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime){
        this.endTime = endTime;
    }
    
    public List<Task> getDependents(){
        return dependents;
    }
    
    public void setDependents(List<Task> dependents){
        this.dependents = dependents;
    }
    
}