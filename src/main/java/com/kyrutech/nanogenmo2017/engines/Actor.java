package com.kyrutech.nanogenmo2017.engines;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    private String firstName;
    private String lastName;
    private Sex sex;
    private Integer age;
    private Motivation motivation;
    private List<String> motivationParameter = new ArrayList<>();
    private List<Trait> positiveTraits = new ArrayList<>();
    private List<Trait> neutralTraits = new ArrayList<>();
    private List<Trait> negativeTraits = new ArrayList<>();


    public enum Sex {
        FEMALE,
        MALE
    }

    public enum Motivation {
        REVENGE,
        GREED,
        SURVIVAL,
        LOVE,
        CURIOSITY,
        DUTY,
        SELFDISCOVERY,
        SELFSATISFACTION
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Motivation getMotivation() {
        return motivation;
    }

    public void setMotivation(Motivation motivation) {
        this.motivation = motivation;
    }

    public List<String> getMotivationParameter() {
        return motivationParameter;
    }

    public void setMotivationParameter(List<String> motivationParameter) {
        this.motivationParameter = motivationParameter;
    }

    public List<Trait> getPositiveTraits() {
        return positiveTraits;
    }

    public void setPositiveTraits(List<Trait> positiveTraits) {
        this.positiveTraits = positiveTraits;
    }

    public List<Trait> getNeutralTraits() {
        return neutralTraits;
    }

    public void setNeutralTraits(List<Trait> neutralTraits) {
        this.neutralTraits = neutralTraits;
    }

    public List<Trait> getNegativeTraits() {
        return negativeTraits;
    }

    public void setNegativeTraits(List<Trait> negativeTraits) {
        this.negativeTraits = negativeTraits;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Actor\n");
        sb.append("firstName: ").append(firstName).append("\n");
        sb.append("lastName: ").append(lastName).append("\n");
        sb.append("sex: ").append(sex).append("\n");
        sb.append("age: ").append(age).append("\n");
        sb.append("motivation: ").append(motivation).append("\n");
        sb.append("motivationParameter: ").append(motivationParameter).append("\n");
        sb.append("positiveTraits: ").append(positiveTraits).append("\n");
        sb.append("neutralTraits: ").append(neutralTraits).append("\n");
        sb.append("negativeTraits: ").append(negativeTraits).append("\n");

        return sb.toString();
    }
}
