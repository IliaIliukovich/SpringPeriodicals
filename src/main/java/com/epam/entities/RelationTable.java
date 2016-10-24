package com.epam.entities;

public class RelationTable implements Comparable<RelationTable> {

    private long id;
    private long id_user;
    private long id_journal;

    public static final String CHOICE_TABLE = "choice";
    public static final String SUBSCRIPTION_TABLE = "subscription";

    public RelationTable(long id, long id_user, long id_journal) {
        this.id = id;
        this.id_user = id_user;
        this.id_journal = id_journal;
    }

    public long getId() {
        return id;
    }

    public long getId_user() {
        return id_user;
    }

    public long getId_journal() {
        return id_journal;
    }

    @Override
    public String toString() {
        return "RelationTable{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", id_journal=" + id_journal +
                '}';
    }

    @Override
    public int compareTo(RelationTable that) {
        return (int) (this.getId_journal() - that.getId_journal());
    }
}
