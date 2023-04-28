package com.epam.dao;

import com.epam.config.TestAppConfig;
import com.epam.entities.Journal;
import com.epam.repository.JournalRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
@Transactional
public class JournalRepositoryTest {

    @Autowired
    private JournalRepository repository;

    @Test
    public void testGetJornal() throws Exception {
        Journal journal = repository.findById(1L).get();
        assertThat(journal.getName(), is("STRF.ru"));
    }

    @Test
    public void testGetAllJournals() throws Exception {
        List<Journal> journals = repository.findAll();
        assertThat(journals, is(notNullValue()));
    }

    @Test
    public  void addNewJournal() throws Exception {
        Journal journal = new Journal(null,"test", "la-la-la", BigDecimal.valueOf(1777));
        repository.save(journal);
        List<Journal> journals = repository.findAll();
        assertThat(journals.size(), is(9));
    }

    @Test
    public  void editJournal() throws Exception {
        Journal journalBeforeTest = repository.findById(1L).get();
        assertThat(journalBeforeTest.getDescription(), is(not("la-la-la")));
        Journal journal = new Journal(1L,"test", "la-la-la", BigDecimal.valueOf(1777));
        repository.save(journal);
        Journal journalAfterTest = repository.findById(1L).get();
        assertThat(journalAfterTest.getDescription(), is("la-la-la"));
    }

}
