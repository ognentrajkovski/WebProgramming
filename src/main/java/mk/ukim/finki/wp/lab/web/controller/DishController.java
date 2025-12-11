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

    // --- CREATE ---
    @PostMapping("/add")
    public String saveDish(@RequestParam String dishId,
                           @RequestParam String name,
                           @RequestParam String cuisine,
                           @RequestParam int preparationTime) {

        dishService.create(dishId, name, cuisine, preparationTime);
        return "redirect:/dishes";
    }

    @GetMapping("/dish-form")
    public String getAddDishPage(Model model) {
        model.addAttribute("dish", new Dish());      // празен објект за форма
        model.addAttribute("action", "/dishes/add"); // POST submit ќе оди тука
        return "dish-form";
    }

    // --- UPDATE ---
    @GetMapping("/dish-form/{id}")
    public String getEditDishForm(@PathVariable Long id, Model model) {
        Dish dish = dishService.findById(id)
                .orElse(null);

        if (dish == null)
            return "redirect:/dishes?error=DishNotFound";

        model.addAttribute("dish", dish);                    // постоечки dish
        model.addAttribute("action", "/dishes/edit/" + id);  // POST submit URL
        return "dish-form";
    }

    @PostMapping("/edit/{id}")
    public String editDish(@PathVariable Long id,
                           @RequestParam String dishId,
                           @RequestParam String name,
                           @RequestParam String cuisine,
                           @RequestParam int preparationTime) {

        // Вчитување од базата, промена на полиња и зачувување
        dishService.update(id, dishId, name, cuisine, preparationTime);
        return "redirect:/dishes";
    }

    // --- DELETE ---
    @GetMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.delete(id);
        return "redirect:/dishes";
    }

    // --- ASSIGN DISH TO CHEF ---
    @GetMapping("/choose")
    public String showDishChoosePage(@RequestParam Long chefId, Model model) {
        Chef chef = chefService.findById(chefId).orElse(null);
        if (chef == null)
            return "redirect:/chefs?error=ChefNotFound";

        model.addAttribute("chef", chef);
        model.addAttribute("dishes", dishService.listDishes());
        return "selectDishForChef";
    }

    @PostMapping("/add-to-chef")
    public String addDishToChef(@RequestParam Long chefId,
                                @RequestParam String dishId) {
        chefService.addDishToChef(chefId, dishId);
        return "redirect:/chefs/details/" + chefId; // Redirect на деталите за шефот
    }

    @GetMapping("/add-to-chef")
    public String showAddDishToChefPage(@RequestParam Long chefId, Model model) {
        Chef chef = chefService.findById(chefId).orElse(null);
        if (chef == null)
            return "redirect:/chefs?error=ChefNotFound";

        model.addAttribute("chef", chef);
        model.addAttribute("dishes", dishService.listDishes());
        return "selectDishForChef";
    }
}
