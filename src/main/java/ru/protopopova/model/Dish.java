package ru.protopopova.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="dishes", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "dishes_unique_name_idx")})
public class Dish extends AbstractNamedEntity {
    public Dish() {
    }

    public Dish(Integer id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
