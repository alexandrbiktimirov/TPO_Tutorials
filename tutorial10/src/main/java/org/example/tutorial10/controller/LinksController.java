package org.example.tutorial10.controller;

import org.example.tutorial10.exception.InvalidPasswordException;
import org.example.tutorial10.exception.LinkDoesNotExistException;
import org.example.tutorial10.model.dto.CreateLinkDTO;
import org.example.tutorial10.model.dto.LinkDTOReturn;
import org.example.tutorial10.model.dto.UpdateLinkDTO;
import org.example.tutorial10.service.LinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/links")
public class LinksController {
    private final LinkService linkService;

    public LinksController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<LinkDTOReturn> createLink(@RequestBody CreateLinkDTO createLinkDTO) {
        var result = linkService.createLink(createLinkDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.id)
                .toUri();

        return ResponseEntity.created(location).body(result);
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
