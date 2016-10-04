package com.epam.dao;

import com.epam.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAO {
    private JdbcTemplate template;

    @Autowired
    public UserDAO(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public User getUser(String username) {
        String sqlTxt = "SELECT * FROM user WHERE login=?";
        return template.queryForObject(sqlTxt, new UserMapper(), username);
    }

    public void createUser(User user) {
        String sqlTxt = "INSERT INTO user(login, password, role) values(?,?,?)";
        template.update(sqlTxt, user.getUsername(), user.getPassword(), user.getRole());
    }

    private class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User(resultSet.getLong("id_user"), resultSet.getString("login"),
                    resultSet.getString("password"), resultSet.getString("role"));
        }
    }
}
