package com.epam.dao;

import com.epam.config.TestAppConfig;
import com.epam.entities.User;
import com.epam.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void testGetUser() throws Exception {
        User user = repository.findByUsername("admin");
        assertThat(user.getPassword(), is("9dddec223b46691738e5e25d671b306d0442c2c6372492fb794f259526399b7d6d8f42657fcbcf1b"));
    }

    @Test
    public  void addNewUser() throws Exception {
        User user = new User(null, "tom2", "la-la-la", "ROLE_USER");
        repository.save(user);
        User user2 = repository.findByUsername("tom2");
        assertThat(user2.getUsername(), is(user.getUsername()));
    }

}
