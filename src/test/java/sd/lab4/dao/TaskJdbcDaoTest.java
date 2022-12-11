package sd.lab4.dao;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import sd.lab4.model.Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class TaskJdbcDaoTest {
    private static TaskJdbcDao dao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:tasks-test.db");
        dataSource.setUsername("");
        dataSource.setPassword("");

        try (Connection c = DriverManager.getConnection("jdbc:sqlite:tasks-test.db")) {
            Statement stmt = c.createStatement();

            stmt.execute("DROP TABLE Tasks");
            stmt.close();

            stmt = c.createStatement();
            stmt.executeUpdate("CREATE TABLE Tasks (\n" +
                    "                                     id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "                                     name VARCHAR(64) NOT NULL,\n" +
                    "                                     summary BLOB NOT NULL,\n" +
                    "                                     completed BOOLEAN DEFAULT false,\n" +
                    "                                     creation_time TIMESTAMP DEFAULT (datetime('now','localtime')),\n" +
                    "                                     completion_time TIMESTAMP DEFAULT null\n" +
                    ");");
        }

        dao = new TaskJdbcDao(dataSource);
    }

    @After
    public void after() {
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
        System.out.println(dao.getUncompletedTasks());
        assertEquals(1, dao.getUncompletedTasks().size());
    }
}
