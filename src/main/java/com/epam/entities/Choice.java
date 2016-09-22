package com.epam.entities;

public class Choice {

    private long id_choice;
    private long id_user;
    private long id_journal;

    public Choice(long id_choice, long id_user, long id_journal) {

        this.id_choice = id_choice;
        this.id_user = id_user;
        this.id_journal = id_journal;
    }

    public long getId_choice() {
        return id_choice;
    }

    public long getId_user() {
        return id_user;
    }

    public long getId_journal() {
        return id_journal;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "id_choice=" + id_choice +
                ", id_user=" + id_user +
                ", id_journal=" + id_journal +
                '}';
    }
}
