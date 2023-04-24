package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V8__insert_testadmin_to_users_and_update_testuser extends BaseJavaMigration {
    public void migrate(Context context) {
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO USERS(id, login, pesel, password, email, name, last_name, phone_number, role) " +
                        "VALUES (2, 'testadmin', '98062267817', '$2a$12$w4YkVfMkwnsS7qAwo18ajuNAi6T.W6mxQQPHVAY6hFMlp.egK0V5m'," +
                        "'test@email.com', 'Testname', 'TestLastName', '777777777', 'ROLE_ADMIN')");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("UPDATE users SET role = 'ROLE_USER' WHERE role = 'USER';");
    }
}
