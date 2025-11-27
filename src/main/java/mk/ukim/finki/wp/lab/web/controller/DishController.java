package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;
    private final ChefService chefService;

    public DishController(DishService dishService, ChefService chefService) {
        this.dishService = dishService;
        this.chefService = chefService;
    }
    @GetMapping
    public String getDishesPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        model.addAttribute("dishes", dishService.listDishes());
        return "listDishes";
    }
    @PostMapping("/add")
    public String saveDish(@RequestParam String dishId,
                           @RequestParam String name,
                           @RequestParam String cuisine,
                           @RequestParam int preparationTime){

        dishService.create(dishId,name,cuisine,preparationTime);
        return "redirect:/dishes";
    }

    @PostMapping("/edit/{id}")
    public String editDish(@PathVariable Long id,
                           @RequestParam String dishId,
                           @RequestParam String name,
                           @RequestParam String cuisine,
                           @RequestParam int preparationTime){
        dishService.update(id,dishId,name,cuisine,preparationTime);
        return "redirect:/dishes";
    }

    @GetMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.delete(id);
        return "redirect:/dishes";
    }

    @GetMapping("/dish-form")
    public String getAddDishPage(Model model) {
        model.addAttribute("dish", new Dish());      // празен објект за форма
        model.addAttribute("action", "/dishes/add"); // POST submit ќе оди тука
        return "dish-form";
    }

    @GetMapping("/dish-form/{id}")
    public String getEditDishForm(@PathVariable Long id, Model model) {
        if(dishService.findById(id).isEmpty())
            return "redirect:/dishes?error=DishNotFound";

        Dish dish = dishService.findById(id).get();


        model.addAttribute("dish", dish);                    // постоечки dish
        model.addAttribute("action", "/dishes/edit/" + id);  // POST submit URL
        return "dish-form";
    }

    @GetMapping("/choose")
    public String showDishChoosePage(@RequestParam Long chefId, Model model) {

        Chef chef = chefService.findById(chefId).get();


        model.addAttribute("chef", chef);
        model.addAttribute("dishes", dishService.listDishes());

        return "selectDishForChef";
    }

    @PostMapping("/add-to-chef")
    public String addDishToChef(@RequestParam Long chefId,
                                @RequestParam String dishId,
                                Model model) {

        Chef chef = chefService.addDishToChef(chefId, dishId);
        model.addAttribute("chef", chef);

        return "chefDetails"; // ова HTML веќе го имаш
    }

    @GetMapping("/add-to-chef")
    public String showAddDishToChefPage(@RequestParam Long chefId, Model model) {
        Chef chef = chefService.findById(chefId).get();

        model.addAttribute("chef", chef);
        model.addAttribute("dishes", dishService.listDishes());

        return "selectDishForChef";
    }
}

