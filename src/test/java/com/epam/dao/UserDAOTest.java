package com.epam.dao;

import com.epam.config.AppConfig;
import com.epam.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class UserDAOTest {

    @Autowired
    private UserDAO dao;

    @Test
    public void testGetUser() throws Exception {
        User user = dao.getUser("admin");
        System.out.println(user);
        assertThat(user.getPassword(), is("admin"));
    }

    @Test
    public  void addNewUser() throws Exception {
        User user = new User(1L, "tom2", "la-la-la", "ROLE_USER");
        dao.createUser(user);
        User user2 = dao.getUser("tom2");
        System.out.println(user2);
        assertThat(user2.getUsername(), is(user.getUsername()));
    }

}
