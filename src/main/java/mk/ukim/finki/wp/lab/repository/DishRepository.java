package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findAllByChef_Id(Long chefId);

    Optional<Dish> findByDishId(String dishId);
}
