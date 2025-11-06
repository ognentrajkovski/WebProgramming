package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "DishServlet", urlPatterns = "/dish")
@Component
public class DishServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final DishService dishService;
    private final ChefService chefService;

    public DishServlet(SpringTemplateEngine springTemplateEngine, DishService dishService, ChefService chefService) {
        this.springTemplateEngine = springTemplateEngine;
        this.dishService = dishService;
        this.chefService = chefService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);


        String chefIdParam = req.getParameter("chefId");
        long chefId = Long.parseLong(chefIdParam);
        Optional<Chef> chef = chefService.findById(chefId);

        WebContext context = new WebContext(webExchange);
        context.setVariable("chefName", chef.get().getFirstName() + " " + chef.get().getLastName());
        context.setVariable("dishes", dishService.listDishes());
        context.setVariable("chefId", chef.get().getId());

        springTemplateEngine.process("dishesList", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String chefId = request.getParameter("chefId");
        response.sendRedirect("/dish?chefId=" + chefId);
    }


}
