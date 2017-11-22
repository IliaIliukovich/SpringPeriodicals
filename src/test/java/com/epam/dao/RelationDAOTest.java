package com.epam.dao;

import com.epam.config.TestAppConfig;
import com.epam.entities.RelationTable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
@Transactional
public class RelationDAOTest {

    @Autowired
    private RelationTableDAO dao;

    @Test
    public void testChoices() throws Exception {
        dao.addRelation(new RelationTable(1L, 2L, 1L), RelationTable.CHOICE_TABLE);
        dao.addRelation(new RelationTable(1L, 2L, 2L), RelationTable.CHOICE_TABLE);
        dao.addRelation(new RelationTable(1L, 2L, 3L), RelationTable.CHOICE_TABLE);
        List<RelationTable> relations = dao.getRelations(2L, RelationTable.CHOICE_TABLE);
        assertThat(relations.size(), is(3));
        dao.deleteRelation(relations.get(0).getId(), RelationTable.CHOICE_TABLE);
        List<RelationTable> relations2 = dao.getRelations(2L, RelationTable.CHOICE_TABLE);
        assertThat(relations2.size(), is(2));
    }

}
