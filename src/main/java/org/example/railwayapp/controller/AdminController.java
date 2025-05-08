package org.example.railwayapp.controller;


import org.example.railwayapp.dto.AdminTripDto;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RailwayDataService railwayDataService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String adminPage(Model model

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
            List<AdminTripDto> tripsAndTickets = railwayDataService.getAllTripsWithTicketsForAdmin();
            model.addAttribute("tripsData", tripsAndTickets);
            model.addAttribute("loggedInUser", loggedInUser); // Передаем объект пользователя для отображения имени и т.п.
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка загрузки данных: " + e.getMessage());
        }
        return "admin";
    }
}