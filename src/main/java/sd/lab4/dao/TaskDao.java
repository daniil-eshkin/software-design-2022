package sd.lab4.dao;

import sd.lab4.model.Task;

import java.util.List;

public interface TaskDao {
    int addTask(Task task);

    List<Task> getTasks();

    List<Task> getUncompletedTasks();

    void completeTask(Task task);

    void clearTasks();
}
