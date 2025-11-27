package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.service.ChefService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chefs")
public class ChefController {

    private final ChefService chefService;

    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }
    @GetMapping
    public String getChefsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        model.addAttribute("chefs", chefService.listChefs());
        return "chefsList";
    }
    @PostMapping("/add")
    public String saveChef(@RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String bio){

        chefService.create(firstName,lastName,bio);
        return "redirect:/chefs";
    }

    @PostMapping("/edit/{id}")
    public String editChef(@PathVariable Long id,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String bio){
        chefService.update(id,firstName,lastName,bio);
        return "redirect:/chefs";
    }

    @GetMapping("/delete/{id}")
    public String deleteChef(@PathVariable Long id) {
        chefService.delete(id);
        return "redirect:/chefs";
    }

    @GetMapping("/chef-form")
    public String getAddChefPage(Model model) {
        model.addAttribute("chef", new Chef());      // празен објект за форма
        model.addAttribute("action", "/chefs/add"); // POST submit ќе оди тука
        return "chef-form";
    }

    @GetMapping("/chef-form/{id}")
    public String getEditChefForm(@PathVariable Long id, Model model) {
        if(chefService.findById(id).isEmpty())
            return "redirect:/chefs?error=ChefNotFound";

        Chef chef = chefService.findById(id).get();


        model.addAttribute("chef", chef);                    // постоечки chef
        model.addAttribute("action", "/chefs/edit/" + id);  // POST submit URL
        return "chef-form";
    }
}

