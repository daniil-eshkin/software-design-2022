package sd.lab4.dao;

import sd.lab4.model.Task;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskInMemoryDao implements TaskDao {
    private final AtomicInteger lastId = new AtomicInteger(0);
    private final List<Task> tasks = new CopyOnWriteArrayList<>();

    @Override
    public int addTask(Task task) {
        int id = lastId.incrementAndGet();
        task.setId(id);
        task.setCompleted(false);
        task.setCreationTime(Timestamp.from(Instant.now()));
        tasks.add(task);
        return id;
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public List<Task> getUncompletedTasks() {
        return tasks.stream().filter(Predicate.not(Task::isCompleted)).collect(Collectors.toList());
    }

    @Override
    public void completeTask(Task task) {
        tasks.stream().filter(t -> t.getId() == task.getId()).forEach(t -> {
            t.setCompleted(true);
            t.setCompletionTime(Timestamp.from(Instant.now()));
        });
    }

    @Override
    public void clearTasks() {
        tasks.clear();
    }
}
