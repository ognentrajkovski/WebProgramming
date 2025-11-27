package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Dish> listDishes() {
        return dishRepository.findAll();
    }

    @Override
    public Optional<Dish> findByDishId(String dishId) {
        return dishRepository.findByDishId(dishId);
    }

    @Override
    public Optional<Dish> findById(Long id) {
        return this.dishRepository.findById(id);
    }

    @Override
    public Dish create(String dishId, String name, String cuisine, int preparationTime) {
        Dish dish = new Dish(dishId, name, cuisine, preparationTime);
        return this.dishRepository.save(dish);
    }

    @Override
    public Dish update(Long id, String dishId, String name, String cuisine, int preparationTime) {
        Dish dish = new Dish(id, dishId, name, cuisine, preparationTime);
        return this.dishRepository.save(dish);
    }

    @Override
    public void delete(Long id) {
        this.dishRepository.deleteById(id);
    }
}
