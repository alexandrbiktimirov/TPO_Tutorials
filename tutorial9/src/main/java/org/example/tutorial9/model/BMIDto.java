package org.example.tutorial9.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement
public class BMIDto {
    private double weight;
    private double height;
    private double bmi;
    private BMIType type;

    public BMIDto() {
    }

    public BMIDto(double weight, double height, double bmi, BMIType type) {
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public BMIType getType() {
        return type;
    }

    public void setType(BMIType type) {
        this.type = type;
    }
}
