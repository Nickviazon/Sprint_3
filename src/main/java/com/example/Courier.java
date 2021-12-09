package com.example;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;

public class Courier {

    public final String login;
    public final String password;
    public final String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandom() {
        HashMap<String, String> randomCouriersFields = generateRandomValues();
        return new Courier(
                randomCouriersFields.get("login"),
                randomCouriersFields.get("password"),
                randomCouriersFields.get("firstName"));
    }

    public static Courier getCourierWithOnlyRequiredFields() {
        HashMap<String, String> randomCouriersFields = generateRandomValues();
        return new Courier(
                randomCouriersFields.get("login"),
                randomCouriersFields.get("password"),
                null);
    }

    public static Courier getCourierWithEmptyCredentials() {
        HashMap<String, String> randomCouriersFields = generateRandomValues();
        return new Courier(null, null, randomCouriersFields.get("firstName"));
    }

    public static Courier getCourierWithoutLogin() {
        HashMap<String, String> randomCouriersFields = generateRandomValues();
        return new Courier(null, randomCouriersFields.get("password"), randomCouriersFields.get("firstName"));
    }

    public static Courier getCourierWithoutPassword() {
        HashMap<String, String> randomCouriersFields = generateRandomValues();
        return new Courier(randomCouriersFields.get("login"), null, randomCouriersFields.get("firstName"));
    }

    public static Courier getCourierWithExactLogin(String login) {
        HashMap<String, String> randomCouriersFields = generateRandomValues();
        return new Courier(login, randomCouriersFields.get("password"), randomCouriersFields.get("firstName"));
    }

    private static HashMap<String, String> generateRandomValues() {
        HashMap<String, String> randomValues = new HashMap<>();
        String[] randomFields = {
                "login",
                "password",
                "firstName"
        };
        for (String field: randomFields) {
            randomValues.put(field, RandomStringUtils.randomAlphabetic(10));
        }
        return randomValues;
    }
}
