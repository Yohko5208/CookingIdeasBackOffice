package com.cooking_ideas_thymeleaf.cooking_ideas_thymeleaf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class InsertController {
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    String insert(@ModelAttribute("plat") Plat plat, Model model, HttpSession session){
        if(session.getAttribute("user")!=null){
            try{
                plat.morph();
                if(plat.insert()){
                    model.addAttribute("success", "Insertion réussie !");
                    System.out.println("Voilààà");
                    return "redirect:insertPlat";
                }
                else{
                    model.addAttribute("error", "Une erreur s'est produite, veuillez réessayer plus tard !");
                    return"redirect:insertPlat";
                }

            }
            catch(Exception e){
                System.out.println("Erreur");
                return "erreur";
            }
        }
        else
            return"redirect:login";


    }

}
