package rnd.sueta;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import javax.sql.DataSource;

@SpringBootTest
class EventMsApplicationTests {

    @MockitoBean
    private DataSource dataSource;

    @MockitoBean
    private JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
	}
}
