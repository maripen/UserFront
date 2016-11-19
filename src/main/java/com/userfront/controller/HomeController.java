package com.userfront.controller;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.service.RoleService;
import com.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by maripen on 2016. 11. 06..
 */
@Controller
public class HomeController {

    private UserService userService;

    @Autowired
    public HomeController(UserService userService, RoleService roleService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        User user = new User();
        model.addAttribute(user);

        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute("user") User user, Model model) {
        if(userService.checkIfUserExists(user.getUsername(), user.getEmail())) {

            if(userService.checkIfUsernameExists(user.getUsername())) {
                model.addAttribute("usernameExists", true);
            }

            if(userService.checkIfEmailExists(user.getEmail())) {
                model.addAttribute("emailExists", true);
            }

            return "signup";
        }

        userService.createUser(user);

        return "redirect:/";
    }

    @RequestMapping("userFront")
    public String userFront(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());

        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("savingsAccount", savingsAccount);

        return "userFront";
    }

}
