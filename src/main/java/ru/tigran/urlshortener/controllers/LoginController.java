package ru.tigran.urlshortener.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.tigran.urlshortener.database.entity.User;
import ru.tigran.urlshortener.database.services.UserService;

import java.util.Objects;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user, Model model) {
        if (user.getUsername().isEmpty())
            model.addAttribute("registrationMessage", "Пожалуйста, введите имя пользователя");
        else if (user.getPassword().isEmpty())
            model.addAttribute("registrationMessage", "Пароль не может быть пустым");
        else if (user.getConfirmPassword().isEmpty())
            model.addAttribute("registrationMessage", "Пожалуйста, повторите пароль");
        else if (!Objects.equals(user.getPassword(), user.getConfirmPassword()))
            model.addAttribute("registrationMessage", "Пароли не совпадают");
        else if (!userService.saveUser(user))
            model.addAttribute("registrationMessage", "Пользователь уже зарегистрирован в системе");
        else
            model.addAttribute("registrationMessage", "Вы успешно зарегистрированы!");
        return login(model);
    }
}
