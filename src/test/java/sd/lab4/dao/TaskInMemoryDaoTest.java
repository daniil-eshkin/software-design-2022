package sd.lab4.dao;

import org.junit.Before;
import org.junit.Test;
import sd.lab4.model.Task;

import static org.junit.Assert.assertEquals;

public class TaskInMemoryDaoTest {
    private final TaskInMemoryDao dao = new TaskInMemoryDao();

    @Before
    public void before() {
        dao.clearTasks();
    }

    @Test
    public void singleTask() {
        dao.addTask(Task.builder()
                        .name("Test1")
                        .summary("1")
                .build());
        assertEquals(1, dao.getTasks().size());
    }

    @Test
    public void multipleTasks() {
        dao.addTask(Task.builder()
                .name("Test1")
                .summary("1")
                .build());
        dao.addTask(Task.builder()
                .name("Test2")
                .summary("2")
                .build());
        assertEquals(2, dao.getTasks().size());

        dao.completeTask(Task.builder().id(1).build());

        assertEquals(2, dao.getTasks().size());
        assertEquals(1, dao.getUncompletedTasks().size());
    }
}
