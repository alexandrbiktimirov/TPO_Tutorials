package org.example.tutorial6.controller;

import org.example.tutorial6.exceptions.InvalidNumberOfEntriesException;
import org.example.tutorial6.model.Person;
import org.example.tutorial6.model.DataDTO;
import org.example.tutorial6.service.FakeDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FakeDataController {
    private final FakeDataService fakeDataService;

    public FakeDataController(FakeDataService fakeDataService) {
        this.fakeDataService = fakeDataService;
    }

    @GetMapping("/generate-data")
    public String mainPage() {
        return "data-faker";
    }

    @PostMapping("/generate-data")
    public String handleGeneration(
            @RequestParam("entries") Integer entries,
            @RequestParam("language") String language,
            @RequestParam(value = "fields", required = false) List<String> fields,
            Model model
    ) {
        DataDTO dto =  new DataDTO(entries, language, fields);
        List<Person> people;
        List<String> headers;

        try{
            people = fakeDataService.generateData(dto);
        } catch(InvalidNumberOfEntriesException e){
            model.addAttribute("exception", e.getMessage());
            return "data-faker";
        }

        headers = fakeDataService.generateHeaders(dto);

        model.addAttribute("headers", headers);
        model.addAttribute("people", people);

        return "data-faker";
    }
}