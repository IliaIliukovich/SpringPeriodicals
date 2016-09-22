package com.epam.dao;

import com.epam.entities.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SubscriptionDAO {
    private JdbcTemplate template;

    @Autowired
    public SubscriptionDAO(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public List<Subscription> getSubscriptions(Long id_user) {
        String sqlTxt = "SELECT * FROM subscription WHERE id_user=?";
        return template.query(sqlTxt, new SubscriptionMapper(), id_user);
    }

    public void addSubscription(Subscription subscription) {
        String sqlTxt = "INSERT INTO subscription(id_user, id_journal) values(?,?)";
        template.update(sqlTxt, subscription.getId_user(), subscription.getId_journal());
    }

    private class SubscriptionMapper implements RowMapper<Subscription> {
        @Override
        public Subscription mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Subscription(resultSet.getLong("id_subscription"), resultSet.getLong("id_user"),
                    resultSet.getLong("id_journal"));
        }
    }
}
