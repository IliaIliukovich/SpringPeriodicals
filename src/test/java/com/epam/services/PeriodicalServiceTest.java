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

//    @Test
//    public  void testAddMyChoice() throws Exception {
//        periodicalService.addMyChoice(1L);
//        periodicalService.addMyChoice(2L);
//        List<Choice> choices = periodicalService.getChoices();
//        choices.forEach(System.out::println);
//        assertThat(choices.size(), is(2));
//    }

//    @Test
//    public  void testDeleteMyChoice() throws Exception {
//        periodicalService.addMyChoice(1L);
//        periodicalService.addMyChoice(2L);
//        List<Choice> choices = periodicalService.getChoices();
//        choices.forEach(System.out::println);
//        periodicalService.deleteMyChoice(choices.get(0).getId_choice());
//        List<Choice> choices2 = periodicalService.getChoices();
//        choices2.forEach(System.out::println);
//        assertThat(choices2.size(), is(1));
//    }

//    @Test
//    public void testGetChoices() throws Exception {
//        periodicalService.addMyChoice(1L);
//        periodicalService.addMyChoice(2L);
//        List<Choice> choices = periodicalService.getChoices();
//        choices.forEach(System.out::println);
//        assertThat(choices.size(), is(2));
//    }

//    @Test
//    public void testGetSubscriptions() throws Exception {
//        periodicalService.addMyChoice(1L);
//        periodicalService.addMyChoice(2L);
//        periodicalService.pay(BigDecimal.valueOf(3500));
//        List<Subscription> subscriptions = periodicalService.getSubscriptions();
//        subscriptions.forEach(System.out::println);
//        assertThat(subscriptions.size(), is(2));
//    }

    @Test
    public void testSumToPay() throws Exception {
        periodicalService.addMyChoice(1L);
        periodicalService.addMyChoice(2L);
        BigDecimal supToPay = periodicalService.sumToPay();
        System.out.println("Sum to pay10: " + supToPay);
        assertThat(supToPay, is(BigDecimal.valueOf(3500)));
    }

//    @Test
//    public void testPays() throws Exception {
//        periodicalService.addMyChoice(1L);
//        periodicalService.addMyChoice(2L);
//        periodicalService.pay(BigDecimal.valueOf(3500));
//        List<Subscription> subscriptions = periodicalService.getSubscriptions();
//        subscriptions.forEach(System.out::println);
//        assertThat(subscriptions.size(), is(2));
//        List<Choice> choices = periodicalService.getChoices();
//        assertTrue(choices.isEmpty());
//    }
}
