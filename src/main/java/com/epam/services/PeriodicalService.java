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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<Journal> journals = journalRepository.findAll();
        if (user != null && user.getId_user() != null) {
            journals.forEach(journal -> {
                Optional<Choice> choice = choiceRepository.findByIdUserAndIdJournal(user.getId_user(), journal.getId_journal());
                if (choice.isPresent()) {
                    journal.setState(Journal.State.CHOSEN);
                    journal.setRelationalTableId(choice.get().getId());
                } else {
                    Optional<Subscription> subscription = subscriptionRepository.findByIdUserAndIdJournal(user.getId_user(), journal.getId_journal());
                    if (subscription.isPresent()) {
                        journal.setState(Journal.State.SUBSCRIBED);
                        journal.setRelationalTableId(subscription.get().getId());
                    } else {
                        journal.setState(Journal.State.UNSUBSCRIBED);
                        journal.setRelationalTableId(0);
                    }
                }
            });
        }
        return journals;
    }

    public void createOrUpdate(Journal journal) {
        journalRepository.save(journal);
    }

    public void addMyChoice(Long id_journal, User user) {
        choiceRepository.save(new Choice(null, user.getId_user(), id_journal));
    }

    public void deleteMyChoice(Long id, User user) {
        choiceRepository.deleteByIdAndIdUser(id, user.getId_user());
    }

    public List<List<Journal>> getUserJournals(User user) {
        List<Journal> journals = getJournals(user);
        List <Journal> choiceJournals = journals.stream().filter(e -> e.getState() == Journal.State.CHOSEN).collect(Collectors.toList());
        List <Journal> subscriptionJournals = journals.stream().filter(e -> e.getState() == Journal.State.SUBSCRIBED).collect(Collectors.toList());
        List <List<Journal>> userJournals = new ArrayList<>();
        userJournals.add(choiceJournals);
        userJournals.add(subscriptionJournals);
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
}
