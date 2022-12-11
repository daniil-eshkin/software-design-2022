package sd.lab4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sd.lab4.dao.TaskJdbcDao;

import javax.sql.DataSource;

@Configuration
public class JdbcDaoContextConfiguration {
    @Bean
    public TaskJdbcDao taskJdbcDao(DataSource dataSource) {
        return new TaskJdbcDao(dataSource);
    }
}
