package com.epam.dao;

import com.epam.config.AppConfig;
import com.epam.entities.Choice;
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
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class ChoiceDAOTest {

    @Autowired
    private ChoiceDAO dao;

    @Test
    public void testChoices() throws Exception {
        dao.addChoice(new Choice(1L, 2L, 1L));
        dao.addChoice(new Choice(1L, 2L, 2L));
        dao.addChoice(new Choice(1L, 2L, 3L));
        List<Choice> choices = dao.getChoices(2L);
        assertThat(choices.size(), is(3));
        dao.deleteChoice(choices.get(0).getId_choice());
        List<Choice> choices2 = dao.getChoices(2L);
        assertThat(choices2.size(), is(2));
    }

}
