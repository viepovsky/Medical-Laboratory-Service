package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V3__insert_to_users_table extends BaseJavaMigration {
    public void migrate(Context context) {
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO USERS(id, login, pesel, password, email, name, last_name, phone_number, role) " +
                        "VALUES (1, 'testuser', '98062267819', '$2a$12$w4YkVfMkwnsS7qAwo18ajuNAi6T.W6mxQQPHVAY6hFMlp.egK0V5m'," +
                        "'test@email.com', 'Testname', 'TestLastName', '777777777', 0)");

    }
}
