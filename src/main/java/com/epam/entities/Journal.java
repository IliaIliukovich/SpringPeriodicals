package com.epam.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotBlank;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Journal {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id_journal;

    @NotBlank(message = "Please, enter name")
    @Length(max = 45, message = "Name length should be no more than 45 symbols")
    private String name;

    private String description;

    @NotNull(message = "Please, enter price")
    @DecimalMin(value = "10", message = "Price must be higher than {value}")
    @DecimalMax(value = "1000000", message = "Nobody will bye such a journal! Max value {value}")
    private BigDecimal price;

    @Transient
    private State state = State.UNSUBSCRIBED;

    @Transient
    private long relationalTableId;

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public long getRelationalTableId() {
        return relationalTableId;
    }

    public void setRelationalTableId(long relationalTableId) {
        this.relationalTableId = relationalTableId;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "id_journal=" + id_journal +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", state=" + state +
                ", relationalTableId=" + relationalTableId +
                '}';
    }

    public enum State {
        SUBSCRIBED,
        UNSUBSCRIBED,
        CHOSEN,
    }
}
