package com.epam.validators;

import com.epam.dao.UserDAO;
import com.epam.entities.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    private UserDAO dao;
    private StandardPasswordEncoder encoder = new StandardPasswordEncoder();

    public UserValidator(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (!user.getPassword().equals(user.getPasswordForConfirmation())) {
            errors.rejectValue("password", "user.password", "Incorrect password. Please try again");
        } else {
            user.setRole("ROLE_USER");
            user.setPassword(encoder.encode(user.getPassword()));
            try {
                dao.createUser(user);
            } catch (DuplicateKeyException exception) {
                errors.rejectValue("username", "user.username", "User with such name already exists");
            } catch (Exception exception) {
                exception.printStackTrace();
                errors.rejectValue("username", "user.username", "An error occured. Please try again");
            }
        }
    }
}
