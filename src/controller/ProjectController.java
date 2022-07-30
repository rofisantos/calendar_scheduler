package calendar_schedule.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import calendar_schedule.model.Project;
import calendar_schedule.model.Task;
import calendar_schedule.view.ProjectView;
import calendar_schedule.view.TaskView;
import calendar_schedule.view.MainView;

public class ProjectController {
    
    MainView mainView;
    ProjectView projectView;
    TaskView taskView;
    List<Project> projects = new ArrayList<>();
    
    Scanner scanner;
    boolean exit = false;
    
    public ProjectController(Scanner scanner){
        this.scanner = scanner;
        this.mainView = new MainView(scanner);
        this.projectView = new ProjectView(scanner);
        this.taskView = new TaskView(scanner);
    }
    
    public void init(){
        boolean exit = false;
        mainView.welcomeView();
        do{
            String input = mainView.entryView();
            if(input.equalsIgnoreCase("exit")){
                exit = true;
                System.out.println("Exiting Calendar Scheduler");
            } else if (input.equalsIgnoreCase("view projects")) {
                viewProjects();
            } else if (input.equalsIgnoreCase("add project")) {
                addProject();
            } else if (input.equalsIgnoreCase("delete project")) {
                deleteProject();
            } else if (input.equalsIgnoreCase("view project")) {
                viewProject();
            } else if (input.equalsIgnoreCase("edit project")) {
                editProject();
            } else {
                System.out.println("Invalid Command");
            }
        }while(!exit);
    }
    
    public void initProjectView(int index){
        boolean exit = false;
        Project project = projects.get(index);
        do{
            String input = projectView.projectView();
            if(input.equalsIgnoreCase("exit")){
                exit = true;
                projects.set(index, project);
                System.out.println("Exiting Project Viewer");
            } else if (input.equalsIgnoreCase("view project")) {
                viewCurrentProject(project);
            } else if (input.equalsIgnoreCase("delete project")) {
                deleteCurrentProject(index);
                exit = true;
                System.out.println("Exiting Project Viewer");
            } else if (input.equalsIgnoreCase("add task")) {
                project = addTask(project);
            } else if (input.equalsIgnoreCase("delete task")) {
                project = deleteTask(project);
            } else if (input.equalsIgnoreCase("edit task")) {
                project = editTask(project);
            } else {
                System.out.println("Invalid Command");
            }
        }while(!exit);
    }
    
    public void initTaskView(int index, Project project){
        boolean exit = false;
        Task task = project.getTasks().get(index);
        do{
            String input = taskView.taskView();
            if(input.equalsIgnoreCase("exit")){
                exit = true;
                project.getTasks().set(index, task);
                System.out.println("Exiting Task Viewer");
            } else if (input.equalsIgnoreCase("view task")) {
                viewCurrentTask(task);
            } else if (input.equalsIgnoreCase("delete task")) {
                deleteCurrentTask(index, project);
                exit = true;
                System.out.println("Exiting Task Viewer");
            } else if (input.equalsIgnoreCase("add dependent")) {
                project.getTasks().set(index, addDependent(project, task));
            } else if (input.equalsIgnoreCase("delete dependent")) {
                project.getTasks().set(index, deleteDependent(task));
            } else {
                System.out.println("Invalid Command");
            }
        }while(!exit);
    }

    public void viewProjects(){
        if(projects.size() > 0){
            for(int i = 0; i < projects.size(); i++){
                Project project = projects.get(i);
                projectView.viewProject(project.getTitle(), project.getDescription());
            }
        } else {
            projectView.noProjectsFound();
        }
        
    }
    
    public void addProject(){
        Map<String, String> projectDescription = projectView.addProject();
        String title = projectDescription.get("title");
        if((isProjectExisting(title) == -1 && projects.size() > 0) || projects.size() == 0){
            Project newProject = new Project(title, projectDescription.get("description"), new ArrayList<>());
            projects.add(newProject);
        }
    }
    
    public void deleteProject(){
        String title = projectView.deleteProject();
        boolean removed = false;
        for(int i = 0; i < projects.size(); i++){
            if(projects.get(i).getTitle().equalsIgnoreCase(title)){
                projects.remove(i);
                removed = true;
                break;
            }
        }
        projectView.viewIsProjectDeleted(title, removed);
    }
    
    public void deleteCurrentProject(int index){
        projects.remove(index);
    }
    
    public void viewProject(){
        String title = projectView.viewProject();
        boolean exist = false;
        Project project = null;
        for(int i = 0; i < projects.size(); i++){
            if(projects.get(i).getTitle().equalsIgnoreCase(title)){
                project = projects.get(i);
                exist = true;
                break;
            }
        }
        if(exist){
            projectView.viewIsProjectExisting(title, project.getDescription(), exist);
            List<Task> tasks = project.getTasks();
            viewTasks(tasks);
        } else {
            projectView.viewIsProjectExisting(title, "", exist);
        }
    }
    
    public void viewCurrentProject(Project project){
        projectView.viewIsProjectExisting(project.getTitle(), project.getDescription(), true);
        List<Task> tasks = project.getTasks();
        viewTasks(tasks);
    }
    
    public void editProject(){
        String title = projectView.editProject();
        int index = isProjectExisting(title);
        if(index > -1){
            initProjectView(index);
        } else {
            projectView.viewIsProjectExisting(title, "", false);
        }
    }
    
    public int isProjectExisting(String title){
        for(int i = 0; i < projects.size(); i++){
            Project project = projects.get(i);
            if(project.getTitle().equalsIgnoreCase(title)){
                projectView.viewIsProjectExisting(title, project.getDescription(), true);
                return i;
            }
        }
        return -1;
    }
    
    public void viewTasks(List<Task> tasks){
        Collections.sort(tasks);
        for(int j = 0; j < tasks.size(); j++){
            Task task = tasks.get(j);
            taskView.viewTask(task.getTitle(), task.getDescription(), task.getStartTime(), task.getEndTime());
            List<Task> dependents = task.getDependents();
            viewTaskDependents(dependents);
        }
    }
    
    public void viewTaskDependents(List<Task> dependents){
        Collections.sort(dependents);
        if(dependents.size() > 0){
            taskView.viewDependents();
            for(int k = 0; k < dependents.size(); k++){
                Task dependent = dependents.get(k);
                taskView.viewTask(dependent.getTitle(), dependent.getDescription(), dependent.getStartTime(), dependent.getEndTime());
            }
        }
    }
    
    public Project addTask(Project project){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        
        Map<String, String> taskDescription = taskView.addTask();
        List<Task> tasks = project.getTasks();
        String title = taskDescription.get("title");
        if((isTaskExisting(title, tasks) == -1 && tasks.size() > 0) || tasks.size() == 0){
            LocalDateTime dateTimeStart = LocalDateTime.parse(taskDescription.get("startTime"), formatter);
            LocalDateTime dateTimeEnd = LocalDateTime.parse(taskDescription.get("endTime"), formatter);
            Task newTask = new Task(title, taskDescription.get("description"), dateTimeStart, dateTimeEnd, new ArrayList<>());
            project.getTasks().add(newTask);
        }
        return project;
    }
    
    public Project deleteCurrentTask(int index, Project project){
        String title = project.getTasks().get(index).getTitle();
        project.getTasks().remove(index);
        List<Task> tasks = project.getTasks();
        for(int i = 0; i < tasks.size(); i++){
            Task task = tasks.get(i);
            tasks.set(i, deleteDependent(task, title));
        }
        project.setTasks(tasks);
        return project;
    }
    
    public Project deleteTask(Project project){
        String title = taskView.deleteTask();
        List<Task> tasks = project.getTasks();
        boolean removed = false;
        for(int i = 0; i < tasks.size(); i++){
            Task task = tasks.get(i);
            if(task.getTitle().equalsIgnoreCase(title)){
                tasks.remove(i);
                i--;
                removed = true;
            } else {
                tasks.set(i, deleteDependent(task, title));
            }
        }
        project.setTasks(tasks);
        taskView.viewIsTaskDeleted(title, removed);
        return project;
    }
    
    public Task deleteDependent(Task task){
        String input = taskView.deleteDependent();
        return deleteDependent(task, input);
    }
    
    public Task deleteDependent(Task task, String removedTask){
        List<Task> dependents = task.getDependents();
        for(int i = 0; i < dependents.size(); i++){
            if(dependents.get(i).getTitle().equalsIgnoreCase(removedTask)){
                dependents.remove(i);
                task.setDependents(dependents);
                break;
            }
        }
        return task;
    }
    
    public int isDependentExisting(String title, List<Task> dependents){
        for(int i = 0; i < dependents.size(); i++){
            Task dependent = dependents.get(i);
            if(dependent.getTitle().equalsIgnoreCase(title)){
                taskView.viewIsDependentExisting(title, dependent.getDescription(), dependent.getStartTime(), dependent.getEndTime(), true);
                return i;
            }
        }
        return -1;
    }
    
    public int isTaskExisting(String title, List<Task> tasks){
        for(int i = 0; i < tasks.size(); i++){
            Task task = tasks.get(i);
            if(task.getTitle().equalsIgnoreCase(title)){
                taskView.viewIsTaskExisting(title, task.getDescription(), task.getStartTime(), task.getEndTime(), true);
                System.out.println(i);
                return i;
            }
        }
        return -1;
    }
    
    public Project editTask(Project project){
        String title = taskView.editTask();
        int index = isTaskExisting(title, project.getTasks());
        if(index > -1){
            initTaskView(index, project);
        } else {
            taskView.viewIsTaskExisting(title, "", null, null, false);
        }
        return project;
    }
    
    public void viewCurrentTask(Task task){
        taskView.viewIsTaskExisting(task.getTitle(), task.getDescription(), task.getStartTime(), task.getEndTime(), true);
        List<Task> dependents = task.getDependents();
        viewTaskDependents(dependents);
    }
    
    public Task addDependent(Project project, Task task){
        String dependentTitle = taskView.addDependent();
        List<Task> dependents = task.getDependents();
        System.out.println(dependents.size());
        if(((isDependentExisting(dependentTitle, dependents) == -1 && dependents.size() > 0) || dependents.size() == 0) && task.getTitle() != dependentTitle){
            int taskIndex = isTaskExisting(dependentTitle, project.getTasks());
            if(taskIndex > -1 && project.getTasks().size() > 0 && eligibleForDependent(project.getTasks().get(taskIndex), task)){
                task.getDependents().add(project.getTasks().get(taskIndex));
            }
        }
        return task;
    }
    
    public boolean eligibleForDependent(Task dependent, Task task){
        return (task.getEndTime().isAfter(dependent.getStartTime()) && task.getStartTime().isAfter(dependent.getStartTime()));
    }
    
}