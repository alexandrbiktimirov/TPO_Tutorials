package org.example.tutorial9.service;

import org.springframework.stereotype.Service;

@Service
public class BMIService {
    public double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }
}
