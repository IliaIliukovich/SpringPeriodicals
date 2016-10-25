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
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

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
        journals = setJournalSubscription(journals, user);
        return journals;
    }

    public void addNewJournal(Journal journal) {
        journalDAO.addJournal(journal);
    }

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

    public List<List<Journal>> getUserJournals(User user) {
        List <Journal> userChoiceJournals = new ArrayList<>();
        List <Journal> userSubscriptionJournals = new ArrayList<>();
        List<Journal> journals = journalDAO.getJournals();
        new GetUserJournals().set(journals, user, userChoiceJournals, userSubscriptionJournals);
        List <List<Journal>> userJournals = new ArrayList<>();
        userJournals.add(userChoiceJournals);
        userJournals.add(userSubscriptionJournals);
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

    private List<Journal> setJournalSubscription(List<Journal> journals, User user) {
        if (user.getId_user() != null) {
           journals = new SetJournalSubscription().set(journals, user, null, null);
        }
        return journals;
    }

    private void journalRelationTemplate(List<RelationTable> relations, Journal[] journalArray,
                                         List<Journal> userJournals, BiConsumer<List<Journal>,Journal> consumer) {
        if (!relations.isEmpty()) {
            RelationTable[] relationsArray = relations.toArray(new RelationTable[relations.size()]);
            Arrays.sort(relationsArray);
            int firstInd = 0;
            for (int i = 0; i < relationsArray.length; i++) {
                for (int j = firstInd; j < journalArray.length; j++) {
                    if (relationsArray[i].getId_journal() == journalArray[j].getId_journal()) {
                        consumer.accept(userJournals, journalArray[j]);
                        firstInd = j + 1;
                    }
                }
            }
        }
    }

    abstract class SetJournalsTemplate {
        final List<Journal> set(List<Journal> journals, User user, List <Journal> userChoiceJournals,
                                        List <Journal> userSubscriptionJournals) {
            List<RelationTable> choices = relationTableDAO.getRelations(user.getId_user(), RelationTable.CHOICE_TABLE);
            List<RelationTable> subscriptions = relationTableDAO.getRelations(user.getId_user(), RelationTable.SUBSCRIPTION_TABLE);
            if (!choices.isEmpty() || !subscriptions.isEmpty()) {
                Journal[] journalArray = journals.toArray(new Journal[journals.size()]);
                Arrays.sort(journalArray);
                journals = action(choices, subscriptions, journalArray, userChoiceJournals, userSubscriptionJournals);
            }
            return journals;
        }
        abstract List<Journal> action(List<RelationTable> choices, List<RelationTable> subscriptions,                                     Journal[] journalArray,
                                      List <Journal> userChoiceJournals, List <Journal> userSubscriptionJournals);
    }

    private class GetUserJournals extends SetJournalsTemplate {
        @Override
        List<Journal> action(List<RelationTable> choices, List<RelationTable> subscriptions, Journal[] journalArray,
                             List <Journal> userChoiceJournals, List <Journal> userSubscriptionJournals) {
            journalRelationTemplate(choices, journalArray, userChoiceJournals, List::add);
            journalRelationTemplate(subscriptions, journalArray, userSubscriptionJournals, List::add);
            return null;
        }
    }

    private class SetJournalSubscription extends SetJournalsTemplate {
        @Override
        List<Journal> action(List<RelationTable> choices, List<RelationTable> subscriptions, Journal[] journalArray,
                             List <Journal> userChoiceJournals, List <Journal> userSubscriptionJournals) {
            List<Journal> journals;
            journalRelationTemplate(choices, journalArray, null, (t, u) -> {u.setSubscription(Journal.CHOSEN);});
            journalRelationTemplate(subscriptions, journalArray, null, (t, u) -> {u.setSubscription(Journal.SUBSCRIBED);});
            journals = Arrays.asList(journalArray);
            return journals;
        }
    }

}
