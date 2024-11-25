package com.example.own_api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class FoodModelAssembler implements  RepresentationModelAssembler<Food, EntityModel<Food>>{

    @Override
    public EntityModel<Food> toModel(Food food) {
        return EntityModel.of(food,
                linkTo(methodOn(FoodController.class).one(food.getId())).withSelfRel(),
                linkTo(methodOn(FoodController.class).all()).withRel("food"));
    }
}
