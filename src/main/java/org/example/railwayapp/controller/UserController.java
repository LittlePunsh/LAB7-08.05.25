package org.example.railwayapp.controller;

import org.example.railwayapp.model.railway.Trip;
import org.example.railwayapp.model.users.User;
import org.example.railwayapp.service.RailwayDataService;
import org.example.railwayapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RailwayDataService railwayDataService;

    @Autowired
    private UserService userService;
    @GetMapping
    public String userPage(Model model
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User loggedInUser = null;
        Optional<User> userOptional = userService.findByUsername(username);
        if(userOptional.isPresent()){
            loggedInUser = userOptional.get();
        }
        if (loggedInUser == null) {
            return "redirect:/logout";
        }

        try {
            List<Trip> trips = railwayDataService.getAllTripsOrdered();
            model.addAttribute("trips", trips);
            model.addAttribute("loggedInUser", loggedInUser);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка загрузки данных: " + e.getMessage());
        }
        return "user";
    }
}
