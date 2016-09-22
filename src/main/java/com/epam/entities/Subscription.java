package com.epam.entities;

public class Subscription {

    private long id_subscription;
    private long id_user;
    private long id_journal;

    public Subscription(long id_subscription, long id_user, long id_journal) {

        this.id_subscription = id_subscription;
        this.id_user = id_user;
        this.id_journal = id_journal;
    }

    public long getId_subscription() {
        return id_subscription;
    }

    public long getId_user() {
        return id_user;
    }

    public long getId_journal() {
        return id_journal;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id_subscription=" + id_subscription +
                ", id_user=" + id_user +
                ", id_journal=" + id_journal +
                '}';
    }

}
