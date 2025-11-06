package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Dish {
    //@Getter
    String dishId;
    String name;
    String cuisine;
    int preparationTime;

    public Dish(String name, String cuisine, int preparationTime) {
        this.dishId = String.valueOf((long)(Math.random() * 1000));
        this.name = name;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
    }
}
