package com.example;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

public class Order {

    public final String firstName;
    public final String lastName;
    public final String address;
    public final String metroStation;
    public final String phone;
    public final Integer rentTime;
    public final String deliveryDate;
    public final String comment;
    public final String[] color;

    public Order(
            String firstName,
            String lastName,
            String address,
            String metroStation,
            String phone,
            Integer rentTime,
            String deliveryDate,
            String comment,
            String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order getRandomOrderWithExactColor(String[] color) {
        HashMap<String, String> randomOrderStringFields = generateRandomStringValues();
        int randomRentTime = new Random().nextInt(100);
        return new Order(
                randomOrderStringFields.get("firstName"),
                randomOrderStringFields.get("lastName"),
                randomOrderStringFields.get("address"),
                randomOrderStringFields.get("metroStation"),
                randomOrderStringFields.get("phone"),
                randomRentTime,
                randomOrderStringFields.get("deliveryDate"),
                randomOrderStringFields.get("comment"),
                color
        );
    }

    private static HashMap<String, String> generateRandomStringValues() {


        HashMap<String, String> randomValues = new HashMap<>();
        String[] randomFields = {
                "firstName",
                "lastName",
                "address",
                "metroStation",
                "phone",
                "comment",
        };
        for (String field: randomFields) {
            randomValues.put(field, RandomStringUtils.randomAlphabetic(10));
        }
        randomValues.put("deliveryDate", LocalDateTime.now().toLocalDate().toString());
        return randomValues;
    }
}
