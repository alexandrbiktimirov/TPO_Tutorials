package org.example.tutorial11.controller;

import org.example.tutorial11.exception.InvalidPasswordException;
import org.example.tutorial11.exception.LinkDoesNotExistException;
import org.example.tutorial11.exception.TargetUrlAlreadyExists;
import org.example.tutorial11.model.dto.CreateLinkDTO;
import org.example.tutorial11.model.dto.LinkDTOReturn;
import org.example.tutorial11.model.dto.UpdateLinkDTO;
import org.example.tutorial11.service.LinkService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/links")
public class LinksAPIController {
    private final LinkService linkService;

    public LinksAPIController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> createLink(@RequestBody CreateLinkDTO createLinkDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            return ResponseEntity.badRequest().body(errors);
        }

        try{
            var result = linkService.createLink(createLinkDTO);
            URI location = URI.create("/links/" + result.id);

            return ResponseEntity.created(location).body(result);
        } catch(TargetUrlAlreadyExists e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<LinkDTOReturn> getLink(@PathVariable String id) {
        try{
            var result = linkService.getLink(id);
            return ResponseEntity.ok(result);
        } catch(LinkDoesNotExistException e){
            return ResponseEntity.notFound().header("Error", e.getMessage()).build();
        }
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateLink(@PathVariable String id, @RequestBody UpdateLinkDTO updateLinkDTO){
        try{
            linkService.updateLink(id, updateLinkDTO);
            return ResponseEntity.noContent().build();
        } catch(LinkDoesNotExistException e){
            return ResponseEntity.notFound().header("Error", e.getMessage()).build();
        } catch(InvalidPasswordException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).header("Reason", e.getMessage()).build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteLink(@PathVariable String id, @RequestBody String password) {
        try{
            linkService.deleteLink(id, password);
            return ResponseEntity.noContent().build();
        } catch(LinkDoesNotExistException e){
            return ResponseEntity.notFound().header("Error", e.getMessage()).build();
        } catch(InvalidPasswordException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).header("Reason", e.getMessage()).build();
        }
    }

}
