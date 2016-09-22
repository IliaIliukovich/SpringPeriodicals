package com.epam.dao;

import com.epam.config.AppConfig;
import com.epam.entities.Journal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class JournalDAOTest {

    @Autowired
    private JournalDAO dao;

    @Test
    public void testGetJornal() throws Exception {
        Journal journal = dao.getJournalbyId(1L);
        System.out.println(journal);
        assertThat(journal.getName(), is("STRF.ru"));
    }

    @Test
    public void testGetAllJournals() throws Exception {
        List<Journal> journals = dao.getJournals();
        for (Journal journal : journals) {
            System.out.println(journal);
        }
        assertThat(journals, is(notNullValue()));
    }

    @Test
    public  void addNewJournal() throws Exception {
        Journal journal1 = new Journal(1L,"test", "la-la-la", BigDecimal.valueOf(1777));
        dao.addJournal(journal1);
        List<Journal> journals = dao.getJournals();
        assertThat(journals.size(), is(9));
    }

}
