package com.philately.web;

import com.philately.stamp.dto.OfferedStampsServiceViewModel;
import com.philately.stamp.dto.PurchasableStampServiceModel;
import com.philately.stamp.service.StampService;
import com.philately.user.dto.LoggedInUserServiceModel;
import com.philately.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;
    private final StampService stampService;

    public HomeController(UserService userService, StampService stampService) {
        this.userService = userService;
        this.stampService = stampService;
    }

    @GetMapping("/")
    public ModelAndView getHomePage(ModelAndView modelAndView, HttpSession session) {
        LoggedInUserServiceModel loggedIn = (LoggedInUserServiceModel) session.getAttribute("user");

        if (loggedIn == null) {
            modelAndView.setViewName("index");
        } else {
            LoggedInUserServiceModel loggedInUser = userService.getLoggedInUser(loggedIn.getId());
            loggedInUser.setWishedStamps(loggedIn.getWishedStamps());
            modelAndView.addObject("loggedIn", loggedInUser);
            modelAndView.setViewName("home");

            List<OfferedStampsServiceViewModel> offeredStamps = stampService.getAllWithOwnerIdNotEqual(loggedIn.getId());
            modelAndView.addObject("offeredStamps", offeredStamps);

        }

        return modelAndView;
    }

}
