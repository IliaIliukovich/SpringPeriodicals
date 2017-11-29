package com.epam.repository;

import com.epam.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByIdUser(long idUser);

    Optional<Subscription> findByIdUserAndIdJournal(long idUser, long idJournal);

}
