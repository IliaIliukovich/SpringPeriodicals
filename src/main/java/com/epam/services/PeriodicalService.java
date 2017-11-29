package com.epam.services;

import com.epam.entities.*;
import com.epam.repository.ChoiceRepository;
import com.epam.repository.JournalRepository;
import com.epam.repository.SubscriptionRepository;
import com.epam.repository.UserRepository;
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

    private final JournalRepository journalRepository;
    private final ChoiceRepository choiceRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Autowired
    public PeriodicalService(JournalRepository journalRepository, ChoiceRepository choiceRepository,
                             SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.journalRepository = journalRepository;
        this.choiceRepository = choiceRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public List<Journal> getJournals(User user) {
        List <Journal> journals = journalRepository.findAll();
        journals = setJournalSubscription(journals, user);
        return journals;
    }

    public void createOrUpdate(Journal journal) {
        journalRepository.save(journal);
    }

    public void addMyChoice(Long id_journal, User user) {
        choiceRepository.save(new Choice(null, user.getId_user(), id_journal));
    }

    public void deleteMyChoice(Long id_journal, User user) {
        List<Choice> choices = choiceRepository.findByIdUser(user.getId_user());
        if (!choices.isEmpty()) {
            choices.forEach(choice -> {
                if (choice.getIdJournal() == id_journal) {
                    choiceRepository.delete(choice.getId());
                }
            });
        }
    }

    public List<List<Journal>> getUserJournals(User user) {
        List <Journal> userChoiceJournals = new ArrayList<>();
        List <Journal> userSubscriptionJournals = new ArrayList<>();
        List<Journal> journals = journalRepository.findAll();
        new GetUserJournals().set(journals, user, userChoiceJournals, userSubscriptionJournals);
        List <List<Journal>> userJournals = new ArrayList<>();
        userJournals.add(userChoiceJournals);
        userJournals.add(userSubscriptionJournals);
        return userJournals;
    }

    public BigDecimal sumToPay(User user) {
        List<Choice> choices = choiceRepository.findByIdUser(user.getId_user());
        if (!choices.isEmpty()) {
            List<Journal> journalList = new ArrayList<>();
            choices.forEach(choice -> journalList.add(journalRepository.findOne((choice.getIdJournal()))));
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
            List<Choice> choices = choiceRepository.findByIdUser(user.getId_user());
            if (!choices.isEmpty()) {
                for (RelationTable choice : choices) {
                    subscriptionRepository.save(new Subscription(null, user.getId_user(), choice.getIdJournal()));
                    choiceRepository.delete(choice.getId());
                }
            }
        }
    }

    public void createUser(User user) { userRepository.save(user); }

    public User getUser(String username) { return userRepository.findByUsername(username); }

    private List<Journal> setJournalSubscription(List<Journal> journals, User user) {
        if (user != null && user.getId_user() != null) {
           journals = new SetJournalSubscription().set(journals, user, null, null);
        }
        return journals;
    }

    private void journalRelationTemplate(List<? extends RelationTable> relations, Journal[] journalArray,
                                         List<Journal> userJournals, BiConsumer<List<Journal>,Journal> consumer) {
        if (!relations.isEmpty()) {
            RelationTable[] relationsArray = relations.toArray(new RelationTable[relations.size()]);
            int firstInd = 0;
            for (int i = 0; i < relationsArray.length; i++) {
                for (int j = firstInd; j < journalArray.length; j++) {
                    if (relationsArray[i].getIdJournal() == journalArray[j].getId_journal()) {
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
            List<Choice> choices = choiceRepository.findByIdUser(user.getId_user());
            List<Subscription> subscriptions = subscriptionRepository.findByIdUser(user.getId_user());
            if (!choices.isEmpty() || !subscriptions.isEmpty()) {
                Journal[] journalArray = journals.toArray(new Journal[journals.size()]);
                journals = action(choices, subscriptions, journalArray, userChoiceJournals, userSubscriptionJournals);
            }
            return journals;
        }
        abstract List<Journal> action(List<Choice> choices, List<Subscription> subscriptions,                                     Journal[] journalArray,
                                      List <Journal> userChoiceJournals, List <Journal> userSubscriptionJournals);
    }

    private class GetUserJournals extends SetJournalsTemplate {
        @Override
        List<Journal> action(List<Choice> choices, List<Subscription> subscriptions, Journal[] journalArray,
                             List <Journal> userChoiceJournals, List <Journal> userSubscriptionJournals) {
            journalRelationTemplate(choices, journalArray, userChoiceJournals, List::add);
            journalRelationTemplate(subscriptions, journalArray, userSubscriptionJournals, List::add);
            return null;
        }
    }

    private class SetJournalSubscription extends SetJournalsTemplate {
        @Override
        List<Journal> action(List<Choice> choices, List<Subscription> subscriptions, Journal[] journalArray,
                             List <Journal> userChoiceJournals, List <Journal> userSubscriptionJournals) {
            List<Journal> journals;
            journalRelationTemplate(choices, journalArray, null, (t, u) -> u.setSubscription(Journal.Subscription.CHOSEN));
            journalRelationTemplate(subscriptions, journalArray, null, (t, u) -> u.setSubscription(Journal.Subscription.SUBSCRIBED));
            journals = Arrays.asList(journalArray);
            return journals;
        }
    }

}
