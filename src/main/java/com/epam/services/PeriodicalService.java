package com.epam.services;

import com.epam.entities.Choice;
import com.epam.entities.Journal;
import com.epam.entities.Subscription;
import com.epam.entities.User;
import com.epam.repository.JournalRepository;
import com.epam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class PeriodicalService {

    private final JournalRepository journalRepository;
    private final UserRepository userRepository;

    @Autowired
    public PeriodicalService(JournalRepository journalRepository, UserRepository userRepository) {
        this.journalRepository = journalRepository;
        this.userRepository = userRepository;
    }

    public List<Journal> getJournals(Long id_user) {
        List<Journal> journals = journalRepository.findAll();
        if (id_user != null) {
            User user = userRepository.getOne(id_user);
            Map<Long, Long> choiceMap = user.getChoices().stream().collect(Collectors.toMap(Choice::getIdJournal, Choice::getId));
            Map<Long, Long> subscriptionMap = user.getSubscriptions().stream().collect(Collectors.toMap(Subscription::getIdJournal, Subscription::getId));
            for (Journal journal : journals) {
                if (choiceMap.containsKey(journal.getId_journal())) {
                    journal.setState(Journal.State.CHOSEN);
                    journal.setRelationalTableId(choiceMap.get(journal.getId_journal()));
                } else if (subscriptionMap.containsKey(journal.getId_journal())) {
                    journal.setState(Journal.State.SUBSCRIBED);
                    journal.setRelationalTableId(subscriptionMap.get(journal.getId_journal()));
                } else {
                    journal.setState(Journal.State.UNSUBSCRIBED);
                    journal.setRelationalTableId(0);
                }
            }
        }
        return journals;
    }

    public void createOrUpdate(Journal journal) {
        journalRepository.save(journal);
    }

    public void addMyChoice(Long id_journal, Long id_user) {
        User user = userRepository.findOne(id_user);
        user.getChoices().add(new Choice(id_journal));
        userRepository.save(user);
    }

    public void deleteMyChoice(Long id, Long id_user) {
        User user = userRepository.findOne(id_user);
        for (Choice choice : user.getChoices()) {
            if (choice.getId().equals(id)) {
                user.getChoices().remove(choice);
                userRepository.save(user);
                return;
            }
        }
    }

    public List<List<Journal>> getUserJournals(Long id_user) {
        List<Journal> journals = getJournals(id_user);
        List <Journal> choiceJournals = journals.stream().filter(e -> e.getState() == Journal.State.CHOSEN).collect(Collectors.toList());
        List <Journal> subscriptionJournals = journals.stream().filter(e -> e.getState() == Journal.State.SUBSCRIBED).collect(Collectors.toList());
        List <List<Journal>> userJournals = new ArrayList<>();
        userJournals.add(choiceJournals);
        userJournals.add(subscriptionJournals);
        return userJournals;
    }

    public BigDecimal sumToPay(Long id_user) {
        List<Choice> choices = userRepository.findOne(id_user).getChoices();
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

    public void pay(BigDecimal sum, Long id_user) {
        User user = userRepository.findOne(id_user);
        if (sum.compareTo(sumToPay(id_user)) == 0) {
            List<Choice> choices = user.getChoices();
            if (!choices.isEmpty()) {
                choices.forEach(e -> {
                    user.getSubscriptions().add(new Subscription(e.getIdJournal()));
                });
                choices.clear();
                userRepository.save(user);
            }
        }
    }

    public void createUser(User user) { userRepository.save(user); }

    public User getUser(String username) { return userRepository.findByUsername(username); }
}
