package sd.lab4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import sd.lab4.dao.TaskDao;
import sd.lab4.model.Task;

@Controller
public class TaskController {
    private final TaskDao taskDao;

    public TaskController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @GetMapping({"/get-uncompleted-tasks", "/"})
    public String getUncompletedTasks(ModelMap map) {
        map.addAttribute("tasks", taskDao.getUncompletedTasks());
        map.addAttribute("new_task", new Task());
        map.addAttribute("show_completed", false);
        return "index";
    }

    @GetMapping("/get-tasks")
    public String getTasks(ModelMap map) {
        map.addAttribute("tasks", taskDao.getTasks());
        map.addAttribute("new_task", new Task());
        map.addAttribute("show_completed", true);
        return "index";
    }

    @PostMapping("/add-task")
    public String addTask(@ModelAttribute("new_task") Task task) {
        taskDao.addTask(task);
        return "redirect:/";
    }

    @PostMapping("/complete-task")
    public String completeTask(@ModelAttribute("task") Task task) {
        taskDao.completeTask(task);
        return "redirect:/";
    }
}
