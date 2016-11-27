package com.userfront.controller;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.service.AccountService;
import com.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by maripen on 2016. 11. 19..
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private UserService userService;
    private AccountService accountService;

    @Autowired
    public AccountController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @RequestMapping("/primaryAccount")
    public String primaryAccount(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();

        model.addAttribute("primaryAccount", primaryAccount);

        return "primaryAccount";
    }

    @RequestMapping("/savingsAccount")
    public String savingsAccount(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("savingsAccount", savingsAccount);

        return "savingsAccount";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.GET)
    public String deposit(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("anount", "");

        return "deposit";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String depositPost(@ModelAttribute("amount") String amount,
                              @ModelAttribute("accountType") String accountType,
                              Principal principal) {

        accountService.deposit(accountType, Double.parseDouble(amount), principal);

        return "redirect:/userFront";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String withdraw(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("anount", "");

        return "withdraw";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdrawPost(@ModelAttribute("amount") String amount,
                              @ModelAttribute("accountType") String accountType,
                              Principal principal) {

        accountService.withdraw(accountType, Double.parseDouble(amount), principal);

        return "redirect:/userFront";
    }
}
