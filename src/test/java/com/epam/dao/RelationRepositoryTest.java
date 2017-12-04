package com.epam.dao;

import com.epam.config.TestAppConfig;
import com.epam.entities.Choice;
import com.epam.entities.Subscription;
import com.epam.entities.User;
import com.epam.repository.ChoiceRepository;
import com.epam.repository.SubscriptionRepository;
import com.epam.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
@Transactional
public class RelationRepositoryTest {

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    public void testRelationRepository() throws Exception {
        User user = userRepository.getOne(2L);
        user.getChoices().add(new Choice(1L));
        user.getChoices().add(new Choice(2L));
        user.getChoices().add(new Choice(3L));
        userRepository.save(user);
        List<Choice> relations = userRepository.getOne(2L).getChoices();
        assertThat(relations.size(), is(3));
        relations.remove(0);
        List<Choice> relations2 = userRepository.getOne(2L).getChoices();
        assertThat(relations2.size(), is(2));

        user = userRepository.getOne(2L);
        user.getSubscriptions().add(new Subscription(1L));
        user.getSubscriptions().add(new Subscription(2L));
        user.getSubscriptions().add(new Subscription(3L));
        List<Subscription> relations3 = userRepository.getOne(2L).getSubscriptions();
        assertThat(relations3.size(), is(3));
        relations3.remove(0);
        List<Subscription> relations4 = userRepository.getOne(2L).getSubscriptions();
        assertThat(relations4.size(), is(2));
    }

}
