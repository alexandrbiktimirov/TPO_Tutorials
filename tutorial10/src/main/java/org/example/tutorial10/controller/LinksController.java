package org.example.tutorial10.controller;

import org.example.tutorial10.model.dto.NewLinkDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api")
public class LinksController {

    @PostMapping
    @ResponseBody
    public NewLinkDTO createLink(@RequestBody NewLinkDTO newLinkDTO) {
        return null;
    }
}
