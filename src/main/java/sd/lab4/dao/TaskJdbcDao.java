package sd.lab4.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import sd.lab4.model.Task;

import javax.sql.DataSource;
import java.util.List;

public class TaskJdbcDao extends JdbcDaoSupport implements TaskDao {
    public TaskJdbcDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public int addTask(Task task) {
        String sql = "INSERT INTO Tasks (name, summary) VALUES (?, ?)";
        return getJdbcTemplate().update(sql, task.getName(), task.getSummary());
    }

    @Override
    public List<Task> getTasks() {
        String sql = "SELECT * FROM Tasks ORDER BY creation_time DESC";
        return getByRequest(sql);
    }

    @Override
    public List<Task> getUncompletedTasks() {
        String sql = "SELECT * FROM Tasks WHERE completed = false ORDER BY creation_time DESC";
        return getByRequest(sql);
    }

    @Override
    public void completeTask(Task task) {
        String sql = "UPDATE Tasks SET completed = true, completion_time = (datetime('now','localtime')) WHERE id = ?";
        getJdbcTemplate().update(sql, task.getId());
    }

    @Override
    public void clearTasks() {
        String sql = "DELETE FROM Tasks where true";
        getJdbcTemplate().update(sql);
    }

    private List<Task> getByRequest(String sql) {
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Task.class));
    }
}
