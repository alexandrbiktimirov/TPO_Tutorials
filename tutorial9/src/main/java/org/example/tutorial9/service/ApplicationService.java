package org.example.tutorial9.service;

import org.example.tutorial9.exception.InvalidAgeException;
import org.example.tutorial9.exception.InvalidGenderException;
import org.example.tutorial9.exception.InvalidHeightException;
import org.example.tutorial9.exception.InvalidWeightException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    public static void validateData(String gender, double weight, double height, int age) throws InvalidWeightException, InvalidHeightException, InvalidGenderException, InvalidAgeException {
        validateData(weight, height);

        if (!gender.equalsIgnoreCase("man") && !gender.equalsIgnoreCase("woman")) {
            throw new InvalidGenderException("Invalid gender provided");
        }
        if (age <= 0) {
            throw new InvalidAgeException("Invalid age provided");
        }
    }

    public static void validateData(double weight, double height) throws InvalidWeightException, InvalidHeightException {
        if (weight <= 0){
            throw new InvalidWeightException("Invalid weight provided");
        }
        if (height <= 0){
            throw new InvalidHeightException("Invalid height provided");
        }
    }

    public int calculateBMR(String gender, double weight, double height, int age){
        if (gender.equalsIgnoreCase("man")){
            return (int)(13.397 * weight + 4.799 * height - 5.677 * age + 88.362);
        } else{
            return (int)(9.247 * weight + 3.098 * height - 4.33 * age + 447.593);
        }
    }

    public double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }
}
