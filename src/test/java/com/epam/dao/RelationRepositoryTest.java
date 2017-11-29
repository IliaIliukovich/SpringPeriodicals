package com.epam.dao;

import com.epam.config.TestAppConfig;
import com.epam.entities.Choice;
import com.epam.entities.Subscription;
import com.epam.repository.ChoiceRepository;
import com.epam.repository.SubscriptionRepository;
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
    private SubscriptionRepository subscriptionRepository;

    @Test
    public void testRelationRepository() throws Exception {
        choiceRepository.save(new Choice(null, 2L, 1L));
        choiceRepository.save(new Choice(null, 2L, 2L));
        choiceRepository.save(new Choice(null, 2L, 3L));
        List<Choice> relations = choiceRepository.findByIdUser(2L);
        assertThat(relations.size(), is(3));
        choiceRepository.delete(relations.get(0).getId());
        List<Choice> relations2 = choiceRepository.findByIdUser(2L);
        assertThat(relations2.size(), is(2));

        subscriptionRepository.save(new Subscription(null, 2L, 1L));
        subscriptionRepository.save(new Subscription(null, 2L, 2L));
        subscriptionRepository.save(new Subscription(null, 2L, 3L));
        List<Subscription> relations3 = subscriptionRepository.findByIdUser(2L);
        assertThat(relations3.size(), is(3));
        subscriptionRepository.delete(relations3.get(0).getId());
        List<Subscription> relations4 = subscriptionRepository.findByIdUser(2L);
        assertThat(relations4.size(), is(2));
    }

}
