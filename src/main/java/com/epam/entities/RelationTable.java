package com.epam.entities;

import javax.persistence.*;

@MappedSuperclass
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class RelationTable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_journal")
    private long idJournal;

    public RelationTable() {}

    public RelationTable(long idJournal) {
        this.idJournal = idJournal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdJournal() {
        return idJournal;
    }

    public void setIdJournal(long idJournal) {
        this.idJournal = idJournal;
    }

    @Override
    public String toString() {
        return "RelationTable{" +
                "id=" + id +
                ", idJournal=" + idJournal +
                '}';
    }
}
