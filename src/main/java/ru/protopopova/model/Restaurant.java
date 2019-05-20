package ru.protopopova.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "address"}, name="restaurants_idx")})
public class Restaurant extends AbstractNamedEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "address", nullable = false)
    private String address;

    @Formula("(select count(v.user_id) from votes v where v.restaurant_id = id and v.registered = CURRENT_DATE)")
    private int votes;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, String address, int votes) {
        super(id, name);
        this.address = address;
        this.votes=votes;
    }

    public Restaurant(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name=restaurant.getName();
        this.address = restaurant.getAddress();
        this.votes = restaurant.getVotes();
    }

    private int getVotes() {
        return votes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Restaurant{" +
                "address='" + address + '\'' +
                ", votes=" + votes +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
