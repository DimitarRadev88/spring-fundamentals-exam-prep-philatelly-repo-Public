package com.philately.web;

import com.philately.stamp.service.StampService;
import com.philately.user.dto.LoggedInUserServiceModel;
import com.philately.user.service.UserService;
import com.philately.web.dto.UserLoginBindingModel;
import com.philately.web.dto.UserRegisterBindingModel;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;
    private final StampService stampService;

    @Autowired
    public UserController(UserService userService, StampService stampService) {
        this.userService = userService;
        this.stampService = stampService;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("userLogin")) {
            model.addAttribute("userLogin", new UserLoginBindingModel());
        }

        return "login";
    }

    @PostMapping("/login")
    public ModelAndView postLogin(ModelAndView modelAndView,
                                  @Valid @ModelAttribute("userLogin") UserLoginBindingModel userLogin,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  HttpSession session) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLogin", bindingResult);
            redirectAttributes.addFlashAttribute("userLogin", userLogin);
            modelAndView.setViewName("redirect:/login");
        } else {
            LoggedInUserServiceModel loggedIn = userService.doLogin(userLogin);
            session.setAttribute("user", loggedIn);
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("userRegister")) {
            model.addAttribute("userRegister", new UserRegisterBindingModel());
        }

        return "register";
    }

    @PostMapping("/register")
    public ModelAndView postLogin(ModelAndView modelAndView,
                                  @Valid @ModelAttribute("userRegister") UserRegisterBindingModel userRegister,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegister", bindingResult);
            redirectAttributes.addFlashAttribute("userRegister", userRegister);
            modelAndView.setViewName("redirect:/register");
        } else {
            String username = userService.doRegister(userRegister);
            UserLoginBindingModel userLogin = new UserLoginBindingModel();
            userLogin.setUsername(username);
            redirectAttributes.addFlashAttribute("userLogin", userLogin);
            modelAndView.setViewName("redirect:/login");
        }

        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }

        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/wishlist/add/{id}")
    public String addToWishlist(@PathVariable UUID id,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        LoggedInUserServiceModel loggedIn = (LoggedInUserServiceModel) session.getAttribute("user");

        if (loggedIn != null) {
            loggedIn.getWishedStamps().add(stampService.getWishedStamp(id));
        }

        return "redirect:/";
    }

    @GetMapping("/wishlist/delete/{id}")
    public String deleteFromWishlist(@PathVariable UUID id,
                                     HttpSession session) {

        LoggedInUserServiceModel loggedIn = (LoggedInUserServiceModel) session.getAttribute("user");

        if (loggedIn != null) {
            loggedIn.getWishedStamps().removeIf(stamp -> stamp.getId().equals(id));
        }

        return "redirect:/";
    }

    @GetMapping("/wishlist/purchaseAll")
    public String deleteFromWishlist(HttpSession session) {

        LoggedInUserServiceModel loggedIn = (LoggedInUserServiceModel) session.getAttribute("user");

        if (loggedIn != null) {
            userService.purchaseStampsForUser(loggedIn.getId(), loggedIn.getWishedStamps());
            loggedIn.getWishedStamps().clear();
        }

        return "redirect:/";
    }

}
