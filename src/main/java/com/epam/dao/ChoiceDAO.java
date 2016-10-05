package com.epam.dao;

import com.epam.entities.Choice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ChoiceDAO {

    private final JdbcTemplate template;

    @Autowired
    public ChoiceDAO(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public List<Choice> getChoices(Long id_user) {
        String sqlTxt = "SELECT * FROM choice WHERE id_user=?";
        return template.query(sqlTxt, new ChoiceMapper(), id_user);
    }

    public Choice getChoice(Long id_choice) {
        String sqlTxt = "SELECT * FROM choice WHERE id_choice=?";
        return template.queryForObject(sqlTxt, new ChoiceMapper(), id_choice);
    }

    public void addChoice(Choice choice) {
        String sqlTxt = "INSERT INTO choice(id_user, id_journal) values(?,?)";
        template.update(sqlTxt, choice.getId_user(), choice.getId_journal());

    }

    public void deleteChoice(Long id_choice) {
        String sqlTxt = "DELETE FROM choice WHERE id_choice=?";
        template.update(sqlTxt, id_choice);

    }

    private class ChoiceMapper implements RowMapper<Choice> {
        @Override
        public Choice mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Choice(resultSet.getLong("id_choice"), resultSet.getLong("id_user"),
                    resultSet.getLong("id_journal"));
        }
    }
}
