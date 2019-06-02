package ru.protopopova.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "address"}, name = "restaurants_idx")})
public class Restaurant extends AbstractNamedEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "address", nullable = false)
    private String address;

    @Formula("(select count(v.user_id) from votes v where v.restaurant_id = id and v.registered = CURRENT_DATE)")
    @JsonInclude
    private int votes;

    public Restaurant() {
        super();

    }

    public Restaurant(Integer id, String name, String address, int votes) {
        super(id, name);
        this.address = address;
        this.votes = votes;
    }

    public Restaurant(Restaurant restaurant) {
        super(restaurant.getId(), restaurant.getName());
        this.address = restaurant.getAddress();
        this.votes = restaurant.getVotes();
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
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
