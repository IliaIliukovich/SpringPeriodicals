package com.epam.services;

import com.epam.config.TestAppConfig;
import com.epam.entities.Journal;
import com.epam.entities.User;
import org.junit.Before;
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
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
@Transactional
public class PeriodicalServiceTest {

    @Autowired
    PeriodicalService periodicalService;

    private User user;

    @Before
    public void init() {
        user = new User(1L,"admin", "admin", "ROLE_ADMIN");
    }

    @Test
    public  void testGetJournals() throws Exception {
        List<Journal> journals = periodicalService.getJournals(null);
        assertThat(journals, is(notNullValue()));
        List<Journal> journals2 = periodicalService.getJournals(user.getId_user());
        assertThat(journals2, is(notNullValue()));
    }

    @Test
    public  void testAddNewJournal() throws Exception {
        periodicalService.createOrUpdate(new Journal(null, "Nachalo1", "la-la-la", BigDecimal.valueOf(1500)));
        List<Journal> journals = periodicalService.getJournals(null);
        assertThat(journals.size(), is(9));
    }

    @Test
    public  void testAddGetMyChoice() throws Exception {
        periodicalService.addMyChoice(1L, user.getId_user());
        periodicalService.addMyChoice(2L, user.getId_user());
        List<Journal> choiceJournals = periodicalService.getUserJournals(user.getId_user()).get(0);
        assertThat(choiceJournals.size(), is(2));
    }

    @Test
    public  void testDeleteMyChoice() throws Exception {
        periodicalService.addMyChoice(1L, user.getId_user());
        periodicalService.addMyChoice(2L, user.getId_user());
        List<Journal> choiceJournals = periodicalService.getUserJournals(user.getId_user()).get(0);
        periodicalService.deleteMyChoice(choiceJournals.get(0).getRelationalTableId(), user.getId_user());
        List<Journal> choiceJournals2 = periodicalService.getUserJournals(user.getId_user()).get(0);
        assertThat(choiceJournals2.size(), is(1));
    }


    @Test
    public void testGetSubscriptions() throws Exception {
        periodicalService.addMyChoice(1L, user.getId_user());
        periodicalService.addMyChoice(2L, user.getId_user());
        periodicalService.pay(BigDecimal.valueOf(3500), user.getId_user());
        List<Journal> subscriptionJournals = periodicalService.getUserJournals(user.getId_user()).get(1);
        assertThat(subscriptionJournals.size(), is(2));
    }

    @Test
    public void testSumToPay() throws Exception {
        periodicalService.addMyChoice(1L, user.getId_user());
        periodicalService.addMyChoice(2L, user.getId_user());
        BigDecimal sumToPay = periodicalService.sumToPay(user.getId_user());
        assertThat(sumToPay, is(BigDecimal.valueOf(350000, 2)));
    }

    @Test
    public void testPays() throws Exception {
        periodicalService.addMyChoice(1L, user.getId_user());
        periodicalService.addMyChoice(2L, user.getId_user());
        periodicalService.pay(BigDecimal.valueOf(3500), user.getId_user());
        List<Journal> subscriptionJournals = periodicalService.getUserJournals(user.getId_user()).get(1);
        assertThat(subscriptionJournals.size(), is(2));
        List<Journal> choiceJournals = periodicalService.getUserJournals(user.getId_user()).get(0);
        assertTrue(choiceJournals.isEmpty());

            System.out.println("Information about current customer\n" +
                            "Customer name            : {}\n" +
                            "Customer contact email   : {}\n" +
                            "Customer uid             : {}");
    }

}
