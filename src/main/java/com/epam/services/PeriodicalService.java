package com.epam.services;

import com.epam.dao.RelationTableDAO;
import com.epam.dao.JournalDAO;
import com.epam.dao.UserDAO;
import com.epam.entities.*;
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
    private final RelationTableDAO relationTableDAO;
    private final UserDAO userDAO;

    @Autowired
    public PeriodicalService(JournalDAO journalDAO, RelationTableDAO relationTableDAO, UserDAO userDAO) {
        this.journalDAO = journalDAO;
        this.relationTableDAO = relationTableDAO;
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

    public void editJournal(Journal journal) {
        if (journal.getId_journal() != null) {
            journalDAO.editJournal(journal);
        }
    }

    public void addMyChoice(Long id_journal, User user) {
        Journal journalById = journalDAO.getJournalbyId(id_journal);
        if (journalById != null) {
            RelationTable relationTable = new RelationTable(1L, user.getId_user(), journalById.getId_journal());
            relationTableDAO.addRelation(relationTable, RelationTable.CHOICE_TABLE);
        }
    }

    public void deleteMyChoice(Long id_journal, User user) {
        List<RelationTable> choices = relationTableDAO.getRelations(user.getId_user(), RelationTable.CHOICE_TABLE);
        if (!choices.isEmpty()) {
            choices.forEach(choice -> {
                if (choice.getId_journal() == id_journal) {
                    relationTableDAO.deleteRelation(choice.getId(), RelationTable.CHOICE_TABLE);
                }
            });
        }
    }

    public List<Journal> getUserJournals(User user, String tableName) {
        List<Journal> journals = journalDAO.getJournals();
        List<RelationTable> relations = relationTableDAO.getRelations(user.getId_user(), tableName);
        List <Journal> userJournals = new LinkedList<>();
        if (!relations.isEmpty()) {
            relations.forEach(choice -> {
                for (Journal journal : journals) {
                    if (choice.getId_journal() == journal.getId_journal()) {
                        userJournals.add(journal);
                    }
                }
            });
        }
        return userJournals;
    }

    public BigDecimal sumToPay(User user) {
        List<RelationTable> choices = relationTableDAO.getRelations(user.getId_user(), RelationTable.CHOICE_TABLE);
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

    public void pay(BigDecimal sum, User user) {
        if (sum.compareTo(sumToPay(user)) == 0) {
            List<RelationTable> choices = relationTableDAO.getRelations(user.getId_user(), RelationTable.CHOICE_TABLE);
            if (!choices.isEmpty()) {
                for (RelationTable choice : choices) {
                    relationTableDAO.addRelation(new RelationTable(1L, user.getId_user(), choice.getId_journal()), RelationTable.SUBSCRIPTION_TABLE);
                    relationTableDAO.deleteRelation(choice.getId(), RelationTable.CHOICE_TABLE);
                }
            }
        }
    }

    public void createUser(User user) { userDAO.createUser(user); }

    public User getUser(String username) { return userDAO.getUser(username); }

    private void setJournalSubscription(List<Journal> journals, User user) {
        if (user.getId_user() != null) {
            List<RelationTable> choices = relationTableDAO.getRelations(user.getId_user(), RelationTable.CHOICE_TABLE);
            if (!choices.isEmpty()) {
                choices.forEach(choice -> journals.forEach(journal -> {
                    if (choice.getId_journal() == journal.getId_journal()) {
                        journal.setSubscription("chosen");
                    }
                }));
            }
            List<RelationTable> subscriptions = relationTableDAO.getRelations(user.getId_user(), RelationTable.SUBSCRIPTION_TABLE);
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
