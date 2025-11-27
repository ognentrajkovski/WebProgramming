package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryDishRepository implements DishRepository {

    @Override
    public List<Dish> findAll() {
        return DataHolder.dishes;
    }

    @Override
    public Optional<Dish> findByDishId(String dishId) {
        return DataHolder.dishes
                .stream()
                .filter(dish -> dish.getDishId()
                        .equals(dishId))
                .findFirst();
    }

    @Override
    public Optional<Dish> findById(Long id) {
        return DataHolder.dishes
                .stream()
                .filter(dish -> dish.getId()
                        .equals(id))
                .findFirst();
    }

    @Override
    public Dish save(Dish dish) {
        if (dish.getId() != null)
            deleteById(dish.getId());
        else {
            dish.setId((long) (Math.random() * 1000));
            dish.setDishId(String.valueOf((long)(Math.random() * 1000)));
        }
        DataHolder.dishes.add(dish);
        return dish;
    }

    @Override
    public void deleteById(Long id) {
        DataHolder.dishes.removeIf(dish -> dish.getId().equals(id));
    }

}
