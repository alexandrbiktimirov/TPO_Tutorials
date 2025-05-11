package org.example.tutorial9.service;

import org.springframework.stereotype.Service;

@Service
public class BMRService {
    public int calculateBMR(String gender, double weight, double height, int age){
        if (gender.equalsIgnoreCase("man")){
            return (int)(13.397 * weight + 4.799 * height - 5.677 * age + 88.362);
        } else{
            return (int)(9.247 * weight + 3.098 * height - 4.33 * age + 447.593);
        }
    }
}
