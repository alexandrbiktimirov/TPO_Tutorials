package org.example.tutorial9.model;

public class BMRDto {
    private String gender;
    private double weight;
    private double height;
    private int age;
    private int bmr;

    public BMRDto() {
    }

    public BMRDto(String gender, double weight, double height, int age, int bmr) {
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.bmr = bmr;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBmr() {
        return bmr;
    }

    public void setBmr(int bmr) {
        this.bmr = bmr;
    }
}
