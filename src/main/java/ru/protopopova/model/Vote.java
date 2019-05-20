package ru.protopopova.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity

@Table(name = "votes")
public class Vote extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    @BatchSize(size = 200)
    Restaurant restaurant;

    @Column(name = "registered", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    LocalDate registered;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @BatchSize(size = 200)
    private User user;

    public Vote() {
    }

    public Vote(int id, LocalDate registered, User user, Restaurant restaurant) {
        super(id);
        this.registered = registered;
        this.user = user;
        this.restaurant = restaurant;
    }
    public Vote(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "restaurant=" + restaurant +
                ", registered=" + registered +
                ", user=" + user +
                ", id=" + id +
                '}';
    }

}
