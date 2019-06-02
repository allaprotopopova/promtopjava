package ru.protopopova.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;

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
        this.registered = LocalDate.now();
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


    @JsonGetter(value = "registered")
    public String getRegisteredString() {
        return registered.toString();
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
