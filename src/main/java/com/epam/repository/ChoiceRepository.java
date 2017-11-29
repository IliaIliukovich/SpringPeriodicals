package com.epam.repository;

import com.epam.entities.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    List<Choice> findByIdUser(long idUser);

    Optional<Choice> findByIdUserAndIdJournal(long idUser, long idJournal);

    void deleteByIdAndIdUser (Long id, long idUser);


}
