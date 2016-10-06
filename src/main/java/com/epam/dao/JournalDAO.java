package com.epam.dao;

import com.epam.entities.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JournalDAO {

    private final JdbcTemplate template;

    @Autowired
    public JournalDAO(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public Journal getJournalbyId(Long id) {
        String sqlTxt = "SELECT * FROM journal WHERE id_journal=?";
        return template.queryForObject(sqlTxt, new JournalMapper(), id);
    }

    public List<Journal> getJournals() {
        String sqlTxt = "SELECT * FROM journal";
        return template.query(sqlTxt, new JournalMapper());
    }

    public void addJournal(Journal journal) {
        String sqlTxt = "INSERT INTO journal(name, description, price) values(?,?,?)";
        template.update(sqlTxt, journal.getName(), journal.getDescription(), journal.getPrice());
    }

    private class JournalMapper implements RowMapper<Journal> {
        @Override
        public Journal mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Journal(resultSet.getLong("id_journal"), resultSet.getString("name"),
                    resultSet.getString("description"), resultSet.getBigDecimal("price"));
        }
    }
}
