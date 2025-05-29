package org.example.tutorial11.controller;

import jakarta.validation.Valid;
import org.example.tutorial11.exception.InvalidPasswordException;
import org.example.tutorial11.exception.LinkDoesNotExistException;
import org.example.tutorial11.exception.TargetUrlAlreadyExists;
import org.example.tutorial11.model.dto.CreateLinkDTO;
import org.example.tutorial11.model.dto.UpdateLinkDTO;
import org.example.tutorial11.service.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class LinksController {

    private final LinkService linkService;

    public LinksController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public String mainPage(){
        return "redirect:/links";
    }

    @GetMapping("/links")
    public String linksPage(Model model){
        model.addAttribute("createLinkDTO", new CreateLinkDTO());

        return "create-link";
    }

    @PostMapping("/links")
    public String createLink(@Valid @ModelAttribute("createLinkDTO") CreateLinkDTO createLinkDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "create-link";
        }

        try{
            var result = linkService.createLink(createLinkDTO);
            model.addAttribute("resultLink", result);

            return "redirect:/links/" + result.id;
        } catch(TargetUrlAlreadyExists e){
            model.addAttribute("reason", e.getMessage());

            return "error";
        }
    }

    @GetMapping("/links/{id}")
    public String getLink(@PathVariable String id, Model model){
        try{
            var result = linkService.getLink(id);
            model.addAttribute("resultDto", result);

            var updateDto = new UpdateLinkDTO();
            updateDto.setName(result.getName());
            updateDto.setTargetUrl(result.getTargetUrl());
            model.addAttribute("updateDto", updateDto);

            return "display-link";
        } catch(LinkDoesNotExistException e){
            model.addAttribute("reason", e.getMessage());

            return "error";
        }
    }

    @PostMapping("/links/{id}/edit")
    public String updateLink(@PathVariable String id, @ModelAttribute("updateDto") UpdateLinkDTO updateLinkDTO, Model model){
        try{
            linkService.updateLink(id, updateLinkDTO);
        } catch(InvalidPasswordException e){
            model.addAttribute("reason", e.getMessage());
            return "error";
        } catch(LinkDoesNotExistException ignored){}

        return "redirect:/links/" + id;
    }

    @PostMapping("/links/{id}/delete")
    public String deleteLink(@PathVariable String id, String password, Model model){
        try{
            linkService.deleteLink(id, password);
        } catch(InvalidPasswordException e){
            model.addAttribute("reason", e.getMessage());

            return "error";
        } catch(LinkDoesNotExistException ignored){}

        return "delete-link";
    }
}
