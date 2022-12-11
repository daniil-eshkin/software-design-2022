package sd.lab4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sd.lab4.dao.TaskDao;
import sd.lab4.dao.TaskInMemoryDao;

//@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public TaskDao taskDao() {
        return new TaskInMemoryDao();
    }
}
