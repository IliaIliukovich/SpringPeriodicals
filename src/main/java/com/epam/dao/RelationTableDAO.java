package com.epam.dao;

import com.epam.entities.RelationTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RelationTableDAO {

    private final JdbcTemplate template;

    @Autowired
    public RelationTableDAO(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public List<RelationTable> getRelations(Long id_user, String tableName) {
        String sqlTxt = "SELECT * FROM " + tableName + " WHERE id_user=? ORDER BY id_journal";
        return template.query(sqlTxt, new RelationTableMapper(), id_user);
    }

    public void addRelation(RelationTable relation, String tableName) {
        String sqlTxt = "INSERT INTO " + tableName + "(id_user, id_journal) values(?,?)";
        template.update(sqlTxt, relation.getId_user(), relation.getId_journal());

    }

    public void deleteRelation(Long id, String tableName) {
        String sqlTxt = "DELETE FROM " + tableName + " WHERE id=?";
        template.update(sqlTxt, id);

    }

    private class RelationTableMapper implements RowMapper<RelationTable> {
        @Override
        public RelationTable mapRow(ResultSet resultSet, int i) throws SQLException {
            return new RelationTable(resultSet.getLong("id"), resultSet.getLong("id_user"),
                    resultSet.getLong("id_journal"));
        }
    }
}
