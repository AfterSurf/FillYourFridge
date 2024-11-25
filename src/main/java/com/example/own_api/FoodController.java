package com.example.own_api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class FoodController {
    private final FoodRepository repository;
    private final FoodModelAssembler assembler;

    FoodController(FoodRepository repository, FoodModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/food")
    CollectionModel<EntityModel<Food>> all() {
        List<EntityModel<Food>> fullFood = repository.findAll().stream() // rename?!
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(fullFood, linkTo(methodOn(FoodController.class).all()).withSelfRel());
    }

    @PostMapping("/food")
    ResponseEntity<?> newFood(@RequestBody Food newFood) {
        EntityModel<Food> entityModel = assembler.toModel(repository.save(newFood));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/food/{id}")
    EntityModel<Food> one(@PathVariable Long id) {
        Food food = repository.findById(id).orElseThrow(() -> new FoodNotFoundException(id));
        return assembler.toModel(food);
    }


    @PutMapping("/food/{id}")
    ResponseEntity<?> replaceFood(@RequestBody Food newFood, @PathVariable Long id) {
        Food updatedFood = repository.findById(id)
                .map(food -> {
                    food.setName(newFood.getName());
                    food.setAmount(newFood.getAmount());
                    return repository.save(food);
                })
                .orElseGet(() -> {
                    return repository.save(newFood);
                });

        EntityModel<Food> entityModel = assembler.toModel(updatedFood);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/food/{id}")
    ResponseEntity<?> deleteFood(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
