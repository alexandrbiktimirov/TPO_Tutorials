package org.example.tutorial9.controller;

import org.example.tutorial9.exception.InvalidAgeException;
import org.example.tutorial9.exception.InvalidGenderException;
import org.example.tutorial9.exception.InvalidHeightException;
import org.example.tutorial9.exception.InvalidWeightException;
import org.example.tutorial9.model.BMIDto;
import org.example.tutorial9.model.BMIType;
import org.example.tutorial9.model.BMRDto;
import org.example.tutorial9.service.ApplicationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping(path = "/BMI", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.TEXT_PLAIN_VALUE
    })
    @ResponseBody
    public ResponseEntity<?> BMI(@RequestParam double weight,
                                 @RequestParam double height,
                                 @RequestHeader(value = "Accept", defaultValue = MediaType.TEXT_PLAIN_VALUE) String format) {
        try {
            applicationService.validateData(weight, height);
        } catch (InvalidWeightException | InvalidHeightException e) {
            return ResponseEntity.badRequest().header("Reason", "Invalid data, weight and height parameters must be positive numbers").body(e.getMessage());
        }

        double result = applicationService.calculateBMI(weight, height);

        if (format.equals("text/plain")) {
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(String.format("%.2f", result));
        }

        BMIType type = applicationService.determineBMIType(result);
        BMIDto bmiDto = new BMIDto(weight, height, Math.round(result), type);

        if(format.equals("application/json")){
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bmiDto);
        } else if (format.equals("application/xml")) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(bmiDto);
        } else{
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(String.format("%.2f", result));
        }
    }

    @GetMapping(path = "/BMR/{gender}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.TEXT_PLAIN_VALUE
    })
    @ResponseBody
    public ResponseEntity<?> BMR(@PathVariable String gender,
                                 @RequestParam double weight,
                                 @RequestParam double height,
                                 @RequestParam int age,
                                 @RequestHeader(value = "Accept", defaultValue = MediaType.TEXT_PLAIN_VALUE) String format) {
        try {
            applicationService.validateData(gender, weight, height, age);
        } catch (InvalidWeightException | InvalidHeightException | InvalidGenderException | InvalidAgeException e) {
            return ResponseEntity.status(499).header("Reason", "Invalid data, weight, height and age parameters must be positive numbers").body(e.getMessage());
        }

        int result = applicationService.calculateBMR(gender, weight, height, age);

        if (format.equals("text/plain")) {
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(result + "kcal");
        }

        BMRDto bmrDto = new BMRDto(gender, weight, height, age, result);

        if(format.equals("application/json")){
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bmrDto);
        } else if (format.equals("application/xml")) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(bmrDto);
        } else{
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(result + "kcal");
        }
    }
}
