package com.epam.entities;

import javax.persistence.Entity;

@Entity
public class Subscription extends RelationTable {

    public Subscription() {}

    public Subscription(Long id, long id_user, long id_journal) {
        super(id, id_user, id_journal);
    }

}
