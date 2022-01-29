package ru.tigran.urlshortener.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tigran.urlshortener.database.entity.UrlInfo;
import ru.tigran.urlshortener.database.services.UrlInfoService;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping(value = "/")
public class MainController {
    @Autowired
    UrlInfoService urlInfoService;

    @GetMapping
    public String home(){
        return "index";
    }

    @PostMapping
    public String generate(@RequestParam String url, Model model){
        UrlInfo urlInfo = urlInfoService.getByUrl(url);
        model.addAttribute("urlInfo", urlInfo);
        return "index";
    }

    @GetMapping("/{hash}")
    public String redirect(@PathVariable String hash){
        try {
            UrlInfo urlInfo = urlInfoService.getByHash(hash);
            urlInfo.increaseRedirectCount();
            urlInfoService.save(urlInfo);
            return "redirect:" + urlInfo.getUrl();
        } catch (EntityNotFoundException e){
            e.printStackTrace();
            return "error";
        }
    }
}
