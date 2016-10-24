package com.epam.entities;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Journal implements Comparable<Journal> {

    private Long id_journal;

    @NotBlank(message = "Please, enter name")
    @Length(max = 45, message = "Name length should be no more than 45 symbols")
    private String name;

    private String description;

    @NotNull(message = "Please, enter price")
    @DecimalMin(value = "10", message = "Price must be higher than {value}")
    @DecimalMax(value = "1000000", message = "Nobody will bye such a journal! Max value {value}")
    private BigDecimal price;

    private String subscription = UNSUBSCRIBED;

    public static final String CHOSEN = "chosen";
    public static final String SUBSCRIBED = "subscribed";
    public static final String UNSUBSCRIBED = "unsubscribed";

    public Journal() {
    }

    public Journal(Long id_journal, String name, String description, BigDecimal price) {
        this.id_journal = id_journal;
        this.name = name;
        this.description = description;
        this.price = price;
    }


    public Long getId_journal() {
        return id_journal;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setId_journal(Long id_journal) {
        this.id_journal = id_journal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "id_journal=" + id_journal +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(Journal that) {
        return (int) (this.getId_journal() - that.getId_journal());
    }
}
