package com.epam.validators;

import com.epam.entities.User;
import com.epam.services.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final StandardPasswordEncoder encoder = new StandardPasswordEncoder();
    private final PeriodicalService periodicalService;

    @Autowired
    public UserValidator(PeriodicalService periodicalService) { this.periodicalService = periodicalService; }

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
                periodicalService.createUser(user);
                User createdUser = periodicalService.getUser(user.getUsername());
                user.setId_user(createdUser.getId_user());
            } catch (DuplicateKeyException exception) {
                errors.rejectValue("username", "user.username", "User with such name already exists");
            } catch (Exception exception) {
                exception.printStackTrace();
                errors.rejectValue("username", "user.username", "An error occured. Please try again");
            }
        }
    }
}
