package com.example.own_api;

public class FoodNotFoundException extends RuntimeException {

    FoodNotFoundException(Long id) {
        super("could not find foot " + id);
    }
}
