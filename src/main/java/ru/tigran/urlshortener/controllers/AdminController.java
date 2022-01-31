package ru.tigran.urlshortener.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tigran.urlshortener.database.services.UrlInfoService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    UrlInfoService urlInfoService;

    @GetMapping
    public String admin(Model model){
        model.addAttribute("urlList", urlInfoService.getAllUrlsWithStats());
        return "admin";
    }

    @PostMapping("/delete")
    @ResponseBody
    public boolean deleteUrl(@RequestParam Long urlId){
        urlInfoService.delete(urlId);
        return true;
    }
}
