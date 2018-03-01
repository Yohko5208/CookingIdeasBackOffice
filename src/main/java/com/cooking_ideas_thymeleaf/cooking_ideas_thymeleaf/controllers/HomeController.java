package com.cooking_ideas_thymeleaf.cooking_ideas_thymeleaf.controllers;

        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;

        import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("home")
    String home(HttpSession session, Model model) {
        Plat p=new Plat();
        if (session.getAttribute("user") != null) {
            try{
                model.addAttribute("liste", p.getAllPlat());
                return "home";
            }
            catch(Exception e){
                return"error";
            }
        }
        else{
            return"redirect:login";
        }
    }
    @GetMapping("insertPlat")
    String insert(Model model, HttpSession session){
        if(session.getAttribute("user")!=null){
            model.addAttribute("plat", new Plat());
            return "insertPlat";
        }
        else
            return"redirect:login";
    }
}
