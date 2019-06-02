package ru.protopopova.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity

@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "created"}, name = "menu_idx")})
public class Menu extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    Restaurant restaurant;

    @Column(name = "created", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    LocalDate created;

    @CollectionTable(name = "menu_dishes", joinColumns = @JoinColumn(name = "menu_id"))
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "dish_id")
    @BatchSize(size = 200)
    List<Dish> dishes;

    public Menu() {
    }

    public Menu(Integer id, Restaurant restaurant, LocalDate created, List<Dish> dishes) {
        super(id);
        this.restaurant = restaurant;
        this.created = created;
        this.dishes = dishes;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getCreated() {
        return created;
    }

    @JsonGetter(value = "created")
    public String getCreatedAsString() {
        return created.toString();
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }


    @Override
    public String toString() {
        return "Menu{ created=" + created +
                ", dishes=" + dishes +
                '}';
    }
}
