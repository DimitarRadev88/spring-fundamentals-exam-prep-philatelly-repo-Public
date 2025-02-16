package com.philately.web;

import com.philately.stamp.model.Stamp;
import com.philately.stamp.service.StampService;
import com.philately.user.dto.LoggedInUserServiceModel;
import com.philately.web.dto.StampAddBindingModel;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/stamps")
public class StampController {

    private final StampService stampService;

    @Autowired
    public StampController(StampService stampService) {
        this.stampService = stampService;
    }

    @GetMapping("/add")
    public String addStampPage(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("stampAdd")) {
            model.addAttribute("stampAdd", new StampAddBindingModel());
        }

        return "add-stamp";
    }

    @PostMapping("/add")
    public ModelAndView addStamp(ModelAndView modelAndView,
                                 @Valid @ModelAttribute("stampAdd") StampAddBindingModel stampAdd,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {

        if (session.getAttribute("user") == null) {
            modelAndView.setViewName("redirect:/");
        } else if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.stampAdd", bindingResult);
            redirectAttributes.addFlashAttribute("stampAdd", stampAdd);
            modelAndView.setViewName("redirect:/stamps/add");
        } else {
            stampService.doAdd(stampAdd,((LoggedInUserServiceModel) session.getAttribute("user")).getId());
            modelAndView.setViewName("redirect:/");
        }


        return modelAndView;
    }

}
