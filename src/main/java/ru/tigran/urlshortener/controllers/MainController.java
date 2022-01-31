package ru.tigran.urlshortener.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tigran.urlshortener.database.entity.RedirectStats;
import ru.tigran.urlshortener.database.entity.UrlInfo;
import ru.tigran.urlshortener.database.services.RedirectStatsService;
import ru.tigran.urlshortener.database.services.UrlInfoService;
import ru.tigran.urlshortener.utils.IpAddressParser;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/")
public class MainController {
    @Autowired
    UrlInfoService urlInfoService;
    @Autowired
    RedirectStatsService redirectStatsService;

    @GetMapping
    public String home(){
        return "index";
    }

    @PostMapping
    public String generate(@RequestParam String url, Model model){
        try{
            UrlInfo urlInfo = urlInfoService.getByUrl(url);
            model.addAttribute("urlInfo", urlInfo);
            return "index";
        } catch (Exception e){
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }

    @GetMapping("/{hash}")
    public String redirect(@PathVariable String hash, HttpServletRequest httpRequest, Model model){
        try {
            UrlInfo urlInfo = urlInfoService.getByHash(hash);
            RedirectStats redirectStats = new RedirectStats(urlInfo.getHash(), IpAddressParser.getClientIpAddress(httpRequest));
            redirectStatsService.save(redirectStats);
            return "redirect:" + urlInfo.getUrl();
        } catch (EntityNotFoundException e){
            model.addAttribute("message", "Страница не существует или срок жизни короткой ссылки истек.");
            return "error";
        }
    }
}
