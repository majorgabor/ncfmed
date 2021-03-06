
package hu.elte.mynews.controller;

import hu.elte.mynews.annotation.Role;
import hu.elte.mynews.entity.News;
import static hu.elte.mynews.entity.User.Role.ADMIN;
import static hu.elte.mynews.entity.User.Role.USER;
import hu.elte.mynews.exception.CommentException;
import hu.elte.mynews.exception.NewsException;
import hu.elte.mynews.exception.UserException;
import hu.elte.mynews.service.NewsService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    
    @GetMapping
    private ResponseEntity<Iterable<News>> getAllNews(){
        return ResponseEntity.ok(newsService.getAllNews());
    }
    
    @GetMapping("/{id}")
    private ResponseEntity<News> getNewsById(@PathVariable long id){
        return ResponseEntity.ok(newsService.getNewsById(id));
    }
    
    @Role({USER, ADMIN})
    @PostMapping("/addnews")
    private ResponseEntity<News> addNews(@RequestBody News news) {
        try{
            return ResponseEntity.ok(newsService.newNews(news));
        } catch (UserException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Role({USER, ADMIN})
    @PutMapping("/{rate}/{id}")
    private ResponseEntity<News> rate(@PathVariable String rate, @PathVariable long id){
        try{
            return ResponseEntity.ok(newsService.rate(id, rate));
        } catch (NewsException ex) {
            return ResponseEntity.badRequest().build();
        } catch (UserException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Role({ADMIN})
    @DeleteMapping("/delete/{id}")
    private ResponseEntity<News> deleteNews(@PathVariable long id){
        try {
            return ResponseEntity.ok(newsService.deleteNews(id));
        } catch (NewsException ex) {
            return ResponseEntity.badRequest().build();
        } catch (UserException ex) {
            return ResponseEntity.badRequest().build();
        } catch (CommentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Role({USER, ADMIN})
    @PutMapping("/report/{id}")
    private ResponseEntity<News> reportNews( @PathVariable long id){
        try{
            return ResponseEntity.ok(newsService.reportNews(id));
        } catch (NewsException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Role({ADMIN})
    @PutMapping("/deletereport/{id}")
    private ResponseEntity<News> deleteReportNews( @PathVariable long id){
        try{
            return ResponseEntity.ok(newsService.deleteReportNews(id));
        } catch (NewsException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
