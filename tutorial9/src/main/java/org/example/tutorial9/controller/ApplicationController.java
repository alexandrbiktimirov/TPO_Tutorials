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
            MediaType.TEXT_PLAIN_VALUE
    })
    @ResponseBody
    public ResponseEntity<String> BMITextPlain(@RequestParam double weight,
                                 @RequestParam double height) {
        try {
            applicationService.validateData(weight, height);
        } catch (InvalidWeightException | InvalidHeightException e) {
            return ResponseEntity.badRequest().header("Reason", "Invalid data, weight and height parameters must be positive numbers").body(e.getMessage());
        }

        double result = applicationService.calculateBMI(weight, height);

        return ResponseEntity.ok().body(String.format("%.2f", result));
    }

    @GetMapping(path = "/BMI", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
    })
    @ResponseBody
    public ResponseEntity<?> BMI(@RequestParam double weight,
                                 @RequestParam double height) {
        try {
            applicationService.validateData(weight, height);
        } catch (InvalidWeightException | InvalidHeightException e) {
            return ResponseEntity.badRequest().header("Reason", "Invalid data, weight and height parameters must be positive numbers").body(e.getMessage());
        }

        double result = applicationService.calculateBMI(weight, height);

        BMIType type = applicationService.determineBMIType(result);
        BMIDto bmiDto = new BMIDto(weight, height, result, type);

        return ResponseEntity.ok().body(bmiDto);
    }

    @GetMapping(path = "/BMR/{gender}", produces = {
            MediaType.TEXT_PLAIN_VALUE
    })
    @ResponseBody
    public ResponseEntity<String> BMRTextPlain(@PathVariable String gender,
                                 @RequestParam double weight,
                                 @RequestParam double height,
                                 @RequestParam int age) {
        try {
            applicationService.validateData(gender, weight, height, age);
        } catch (InvalidWeightException | InvalidHeightException | InvalidAgeException e) {
            return ResponseEntity.status(499).header("Reason", "Invalid data, weight, height and age parameters must be positive numbers").body(e.getMessage());
        } catch(InvalidGenderException e){
            return ResponseEntity.badRequest().header("Reason", "Invalid gender data").body(e.getMessage());
        }

        int result = applicationService.calculateBMR(gender, weight, height, age);

        return ResponseEntity.ok().body(result + "kcal");
    }

    @GetMapping(path = "/BMR/{gender}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
    })
    @ResponseBody
    public ResponseEntity<?> BMR(@PathVariable String gender,
                                 @RequestParam double weight,
                                 @RequestParam double height,
                                 @RequestParam int age) {
        try {
            applicationService.validateData(gender, weight, height, age);
        } catch (InvalidWeightException | InvalidHeightException | InvalidAgeException e) {
            return ResponseEntity.status(499).header("Reason", "Invalid data, weight, height and age parameters must be positive numbers").body(e.getMessage());
        } catch(InvalidGenderException e){
        return ResponseEntity.badRequest().header("Reason", "Invalid gender data").body(e.getMessage());
        }

        int result = applicationService.calculateBMR(gender, weight, height, age);

        BMRDto bmrDto = new BMRDto(gender, weight, height, age, result);
        return ResponseEntity.ok().body(bmrDto);
    }
}