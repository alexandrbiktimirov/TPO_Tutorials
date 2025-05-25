package org.example.tutorial11.controller;

import org.example.tutorial11.exception.LinkDoesNotExistException;
import org.example.tutorial11.service.LinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/red")
public class RedirectAPIController {
    private final LinkService linkService;

    public RedirectAPIController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> getRedirectLink(@PathVariable String id){
        try{
            String link = linkService.getRedirectLink(id);

            return ResponseEntity.status(HttpStatus.FOUND).body(link);
        } catch(LinkDoesNotExistException e){
            return ResponseEntity.notFound().header("Error", e.getMessage()).build();
        }
    }
}
