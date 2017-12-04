package com.epam.entities;

import javax.persistence.Entity;

@Entity
public class Choice extends RelationTable {

    public Choice() {}

    public Choice(long id_journal) {
        super(id_journal);
    }

}
