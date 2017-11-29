package com.epam.repository;

import com.epam.entities.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    List<Choice> findByIdUser(long user);

}
