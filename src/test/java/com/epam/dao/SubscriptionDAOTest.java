package com.epam.dao;

import com.epam.config.AppConfig;
import com.epam.entities.Subscription;
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
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class SubscriptionDAOTest {

    @Autowired
    private SubscriptionDAO dao;

    @Test
    public void testSubscriptions() throws Exception {
        Subscription subscription1 = new Subscription(1L, 2L, 1L);
        Subscription subscription2 = new Subscription(1L, 2L, 2L);
        Subscription subscription3 = new Subscription(1L, 2L, 3L);
        dao.addSubscription(subscription1);
        dao.addSubscription(subscription2);
        dao.addSubscription(subscription3);
        List<Subscription> subscriptions = dao.getSubscriptions(2L);
        assertThat(subscriptions.size(), is(3));
    }

}
