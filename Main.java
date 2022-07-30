import calendar_schedule.view.MainView;
import calendar_schedule.controller.ProjectController;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        
        Scanner scanner = new Scanner(System.in);
        ProjectController controller = new ProjectController(scanner);
        controller.init();
        
    }
}
