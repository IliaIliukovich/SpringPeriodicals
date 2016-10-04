package com.epam.services;

import com.epam.config.AppConfig;
import com.epam.entities.Choice;
import com.epam.entities.Journal;
import com.epam.entities.Subscription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class PeriodicalServiceTest {
    @Autowired
    PeriodicalService periodicalService;

    @Test
    public  void testGetJournals() throws Exception {
        List<Journal> journals = periodicalService.getJournals();
        journals.forEach(System.out::println);
        assertThat(journals, is(notNullValue()));
    }

    @Test
    public  void testAddNewJournal() throws Exception {
        periodicalService.addNewJournal(new Journal(1L, "Nachalo1", "la-la-la", BigDecimal.valueOf(1500)));
        List<Journal> journals = periodicalService.getJournals();
        journals.forEach(System.out::println);
        assertThat(journals.size(), is(9));
    }

    @Test
    public  void testAddGetMyChoice() throws Exception {
        periodicalService.addMyChoice(1L);
        periodicalService.addMyChoice(2L);
        List<Journal> choiceJournals = periodicalService.getChoiceJournals();
        choiceJournals.forEach(System.out::println);
        assertThat(choiceJournals.size(), is(2));
    }

    @Test
    public  void testDeleteMyChoice() throws Exception {
        periodicalService.addMyChoice(1L);
        periodicalService.addMyChoice(2L);
        List<Journal> choiceJournals = periodicalService.getChoiceJournals();
        choiceJournals.forEach(System.out::println);
        periodicalService.deleteMyChoice(choiceJournals.get(0).getId_journal());
        List<Journal> choiceJournals2 = periodicalService.getChoiceJournals();
        choiceJournals2.forEach(System.out::println);
        assertThat(choiceJournals2.size(), is(1));
    }


    @Test
    public void testGetSubscriptions() throws Exception {
        periodicalService.addMyChoice(1L);
        periodicalService.addMyChoice(2L);
        periodicalService.pay(BigDecimal.valueOf(3500));
        List<Journal> subscriptionJournals = periodicalService.getSubscriptionJournals();
        subscriptionJournals.forEach(System.out::println);
        assertThat(subscriptionJournals.size(), is(2));
    }

    @Test
    public void testSumToPay() throws Exception {
        periodicalService.addMyChoice(1L);
        periodicalService.addMyChoice(2L);
        BigDecimal supToPay = periodicalService.sumToPay();
        System.out.println("Sum to pay: " + supToPay);
        assertThat(supToPay, is(BigDecimal.valueOf(3500)));
    }

    @Test
    public void testPays() throws Exception {
        periodicalService.addMyChoice(1L);
        periodicalService.addMyChoice(2L);
        periodicalService.pay(BigDecimal.valueOf(3500));
        List<Journal> subscriptionJournals = periodicalService.getSubscriptionJournals();
        subscriptionJournals.forEach(System.out::println);
        assertThat(subscriptionJournals.size(), is(2));
        List<Journal> choiceJournals = periodicalService.getChoiceJournals();
        assertTrue(choiceJournals.isEmpty());
    }
}
