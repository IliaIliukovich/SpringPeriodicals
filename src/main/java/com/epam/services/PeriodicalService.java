package com.epam.services;

import com.epam.dao.ChoiceDAO;
import com.epam.dao.JournalDAO;
import com.epam.dao.SubscriptionDAO;
import com.epam.dao.UserDAO;
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

    private final JournalDAO journalDAO;
    private final ChoiceDAO choiceDAO;
    private final SubscriptionDAO subscriptionDAO;
    private final UserDAO userDAO;

    @Autowired
    public PeriodicalService(JournalDAO journalDAO, ChoiceDAO choiceDAO, SubscriptionDAO subscriptionDAO, UserDAO userDAO) {
        this.journalDAO = journalDAO;
        this.choiceDAO = choiceDAO;
        this.subscriptionDAO = subscriptionDAO;
        this.userDAO = userDAO;
    }

    public List<Journal> getJournals() {
        return journalDAO.getJournals();
    }

    public List<Journal> getJournals(User user) {
        List <Journal> journals = getJournals();
        setJournalSubscription(journals, user);
        return journals;
    }

    public void addNewJournal(Journal journal) { journalDAO.addJournal(journal); }

    public void addMyChoice(Long id_journal, User user) {
        Journal journalById = journalDAO.getJournalbyId(id_journal);
        if (journalById != null) {
            Choice choice = new Choice(1L, user.getId_user(), journalById.getId_journal());
            choiceDAO.addChoice(choice);
        }
    }

    public void deleteMyChoice(Long id_journal, User user) {
        List<Choice> choices = choiceDAO.getChoices(user.getId_user());
        if (!choices.isEmpty()) {
            choices.forEach(choice -> {
                if (choice.getId_journal() == id_journal) {
                    choiceDAO.deleteChoice(choice.getId_choice());
                }
            });
        }
    }

    public List<Journal> getChoiceJournals(User user) {
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

    public List<Journal> getSubscriptionJournals(User user) {
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

    public BigDecimal sumToPay(User user) {
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

    public void pay(BigDecimal sum, User user) throws Exception {
        if (sum.equals(sumToPay(user))) {
            List<Choice> choices = choiceDAO.getChoices(user.getId_user());
            if (!choices.isEmpty()) {
                for (Choice choice : choices) {
                    subscriptionDAO.addSubscription(new Subscription(1L, user.getId_user(), choice.getId_journal()));
                    choiceDAO.deleteChoice(choice.getId_choice());
                }
            }
        } else {
            throw new Exception("Incorrect sum for payment");
        }
    }

    public void createUser(User user) { userDAO.createUser(user); }

    public User getUser(String username) { return userDAO.getUser(username); }

    private void setJournalSubscription(List<Journal> journals, User user) {
        if (user.getId_user() != null) {
            List<Choice> choices = choiceDAO.getChoices(user.getId_user());
            if (!choices.isEmpty()) {
                choices.forEach(choice -> journals.forEach(journal -> {
                    if (choice.getId_journal() == journal.getId_journal()) {
                        journal.setSubscription("chosen");
                    }
                }));
            }
            List<Subscription> subscriptions = subscriptionDAO.getSubscriptions(user.getId_user());
            if (!subscriptions.isEmpty()) {
                subscriptions.forEach(subscription -> journals.forEach(journal -> {
                    if (subscription.getId_journal() == journal.getId_journal()) {
                        journal.setSubscription("subscribed");
                    }
                }));
            }
        }
    }

}
