package com.epam.services;

import com.epam.dao.ChoiceDAO;
import com.epam.dao.JournalDAO;
import com.epam.dao.SubscriptionDAO;
import com.epam.entities.Choice;
import com.epam.entities.Journal;
import com.epam.entities.Subscription;
import com.epam.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class PeriodicalService {

    @Autowired
    JournalDAO journalDAO;
    @Autowired
    ChoiceDAO choiceDAO;
    @Autowired
    SubscriptionDAO subscriptionDAO;

    private User user = new User(2L, "tom", "tom", "ROLE_USER");

    public List<Journal> getJournals() {
        List <Journal> journals = journalDAO.getJournals();
        setJournalSubscription(journals);
        return journals;
    }

    public void addNewJournal(Journal journal) {
        journalDAO.addJournal(journal);
    }

    public void addMyChoice(Long id_journal) {
        Journal journalById = journalDAO.getJournalbyId(id_journal);
        if (!journalById.equals(null)) {
            Choice choice = new Choice(1L, user.getId_user(), journalById.getId_journal());
            choiceDAO.addChoice(choice);
        }
    }

    public void deleteMyChoice(Long id_journal) {
        List<Choice> choices = choiceDAO.getChoices(user.getId_user());
        if (!choices.isEmpty()) {
            choices.forEach(choice -> {
                if (choice.getId_journal() == id_journal) {
                    choiceDAO.deleteChoice(choice.getId_choice());
                }
            });
        }
    }

    public List<Journal> getChoiceJournals() {
        List<Journal> journals = journalDAO.getJournals();
        List<Choice> choices = choiceDAO.getChoices(user.getId_user());
        List <Journal> myChoiceJournals = new LinkedList<>();
        if (!choices.isEmpty()) {
            choices.forEach(choice -> {
                for (Journal journal : journals) {
                    if (choice.getId_journal() == journal.getId_journal()) {
                        myChoiceJournals.add(journal);
                    }
                }
            });
        }
        return myChoiceJournals;
    }

    public List<Journal> getSubscriptionJournals() {
        List<Journal> journals = journalDAO.getJournals();
        List<Subscription> subscriptions = subscriptionDAO.getSubscriptions(user.getId_user());
        List <Journal> mySubscriptionJournals = new LinkedList<>();
        if (!subscriptions.isEmpty()) {
            subscriptions.forEach(subscription -> {
                for (Journal journal : journals) {
                    if (subscription.getId_journal() == journal.getId_journal()) {
                        mySubscriptionJournals.add(journal);
                    }
                }
            });
        }
        return mySubscriptionJournals;
    }

    public BigDecimal sumToPay() {
        List<Choice> choices = choiceDAO.getChoices(user.getId_user());
        if (!choices.isEmpty()) {
            List<Journal> journalList = new ArrayList<>();
            choices.forEach(choice -> journalList.add(journalDAO.getJournalbyId((choice.getId_journal()))));
            BigDecimal sum = BigDecimal.ZERO;
            for (Journal journal : journalList) {
                sum = sum.add(journal.getPrice());
            }
            return sum;
        }
        return BigDecimal.ZERO;
    }

    public void pay(BigDecimal sum) {
        if (sum.equals(sumToPay())) {
            List<Choice> choices = choiceDAO.getChoices(user.getId_user());
            if (!choices.isEmpty()) {
                for (Choice choice : choices) {
                    subscriptionDAO.addSubscription(new Subscription(1L, user.getId_user(), choice.getId_journal()));
                    choiceDAO.deleteChoice(choice.getId_choice());
                }
            }
        }
    }

    private void setJournalSubscription(List<Journal> journals) {
        List<Choice> choices = choiceDAO.getChoices(user.getId_user());
        if (!choices.isEmpty()) {
            choices.forEach(choice -> {
                journals.forEach(journal -> {
                    if (choice.getId_journal() == journal.getId_journal()) {
                        journal.setSubscription("chosen");
                    }
                });
            });
        }
        List<Subscription> subscriptions = subscriptionDAO.getSubscriptions(user.getId_user());
        if (!subscriptions.isEmpty()) {
            subscriptions.forEach(subscription -> {
                journals.forEach(journal -> {
                    if (subscription.getId_journal() == journal.getId_journal()) {
                        journal.setSubscription("subscribed");
                    }
                });
            });
        }
    }
}
