package com.epam.entities;

import javax.persistence.Entity;

@Entity
public class Subscription extends RelationTable {

    public Subscription() {}

    public Subscription(long id_journal) {
        super(id_journal);
    }

}
