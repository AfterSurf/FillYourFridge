package com.example.own_api;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Food {

    private @Id // primary key
    @GeneratedValue Long id;
    private String name;
    private Double amount;
    // ... more Role = Fruit vs Vegi

    Food(){};

    Food(String name, Double amount){
        this.name = name;
        this.amount = amount;
    }

    public Long getId(){
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount(){
        return this.amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if(!(o instanceof Food)) {
            return false;
        }
        Food food = (Food) o;
        return Objects.equals(this.id,food.id) && Objects.equals(this.name,food.name) && Objects.equals(this.amount,food.amount);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.name, this.amount);
    }

    @Override
    public String toString(){
        return "Food {" + "id=" + this.id + ", name='" + this.name + '\'' + ", amount=" + this.amount + "}";
    }

}
