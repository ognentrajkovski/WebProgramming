package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Chef> chefs = new ArrayList<>();
    public static List<Dish> dishes = new ArrayList<>();

    @PostConstruct
    public void init(){
        chefs.add(new Chef("Ognen", "Trajkovski", "Sve"));
        chefs.add(new Chef("David", "Hristov", "Maratonec"));
        chefs.add(new Chef("Marko", "Poposki", "Krosfiter"));
        chefs.add(new Chef("Nikola", "Dulev", "Lol igrac"));
        chefs.add(new Chef("Stefan", "Tagarski", "Nba igrac"));

        dishes.add(new Dish("Karbonara", "Italijanska", 30));
        dishes.add(new Dish("Burger", "Amerikanska", 30));
        dishes.add(new Dish("Oso Buko", "Italijanska", 60));
        dishes.add(new Dish("Tako", "Meksikanska", 10));
        dishes.add(new Dish("Ajvar", "Makedonska", 25));
    }
}
