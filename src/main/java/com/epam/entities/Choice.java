package com.epam.entities;

import javax.persistence.Entity;

@Entity
public class Choice extends RelationTable {

    public Choice(Long id, long id_user, long id_journal) {
        super(id, id_user, id_journal);
    }

}
