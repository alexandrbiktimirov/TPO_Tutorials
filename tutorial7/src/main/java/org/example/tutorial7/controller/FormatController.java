package org.example.tutorial7.controller;

import com.google.googlejavaformat.java.FormatterException;
import org.example.tutorial7.exception.CodeNotFoundException;
import org.example.tutorial7.model.SourceCodeDTO;
import org.example.tutorial7.service.FormatCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
public class FormatController {
    private final FormatCodeService formatCodeService;

    public FormatController(FormatCodeService formatCodeService) {
        this.formatCodeService = formatCodeService;
    }

    @GetMapping("/format-code")
    public String mainPage(Model model){
        model.addAttribute("sourceCodeDTO", new SourceCodeDTO());
        return "format-code";
    }

    @PostMapping("/view-code")
    public RedirectView viewCode(String id){
        return new RedirectView("/format-code/" + id);
    }

    @PostMapping("/format-code")
    public String formatCode(SourceCodeDTO sourceCodeDTO, Model model){
        try {
            formatCodeService.saveAndScheduleDeletion(sourceCodeDTO);
            model.addAttribute("formattedCode", formatCodeService.loadFormattedCode(sourceCodeDTO.getId()));
            model.addAttribute("originalCode", formatCodeService.loadOriginalCode(sourceCodeDTO.getId()));
        } catch (FormatterException e) {
            model.addAttribute("errorMessage", "Invalid code provided");
            return "error";
        } catch (IOException e) {
            model.addAttribute("errorMessage", "I/O exception occurred");
            return "error";
        } catch (ClassNotFoundException e) {
            model.addAttribute("errorMessage", "ClassNotFoundException occurred");
        } catch(CodeNotFoundException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }

        return "format-code";
    }

    @GetMapping("/format-code/{id}")
    public String formatCode(@PathVariable String id, Model model){
        try{
            model.addAttribute("formattedCode", formatCodeService.loadFormattedCode(id));
            model.addAttribute("originalCode", formatCodeService.loadOriginalCode(id));
        } catch (CodeNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        } catch (IOException e) {
            model.addAttribute("errorMessage", "I/O exception occurred");
            return "error";
        } catch (ClassNotFoundException e) {
            model.addAttribute("errorMessage", "ClassNotFoundException occurred");
            return "error";
        }

        return "view-code";
    }
}