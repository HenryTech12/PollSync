package com.votingapi.demo.controller;

import com.votingapi.demo.dto.ArticlesDTO;
import com.votingapi.demo.dto.NewsDTO;
import com.votingapi.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/news")
    public String getNews(Model model) throws IOException, InterruptedException {
        NewsDTO newsDTOList = newsService.readJSON();
        if(newsDTOList.getTotalResults() > 0) {
            update(model, newsDTOList);
        }
        else {
            update(model, newsDTOList);
            model.addAttribute("error", "Nothing to be found on your search!");
        }
        return "newsletter";
    }

    @GetMapping("/search/news")
    public String search(Model model, String q) throws IOException, InterruptedException {
        newsService.updateParameters(q);
        NewsDTO newsDTOList =  newsService.readJSON();
        if(newsDTOList.getTotalResults() > 0) {
            update(model, newsDTOList);
        }
        else {
            update(model,newsDTOList);
            model.addAttribute("error", "Nothing to be found on your search");
        }
        return "newsletter";
    }

    public void update(Model model, NewsDTO newsDTOList) {
        model.addAttribute("newsList", newsDTOList);
        model.addAttribute("news", new ArticlesDTO());
    }
}
