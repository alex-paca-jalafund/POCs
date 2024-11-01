package org.example;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class Animal {

    public void eat() {
        System.out.println("I can eat");
    }
}

class Dog extends Animal {

    @Override
    public void eat() {
        super.eat();
        System.out.println("I eat dog food");
    }

    public void bark() {
        System.out.println("I can bark");
    }
}

class Cat extends Animal {

    @Override
    public void eat() {
        super.eat();
        System.out.println("I eat cat food");
    }

    public void miau() {
        System.out.println("I can maullar");
    }
}

class House {

    Animal animal;

    public House(Animal animal) {
        this.animal = animal;
    }

    public void Dog() {
        if (animal instanceof Dog) {
            ((Dog) animal).bark();
        } else {
            System.out.println("There is no Dog");
        }
    }

    public void Cat(){
        System.out.println("miau");
    }
}

class MainExample {

    public static void main(String[] args) {
        Dog labrador = new Dog();
        labrador.eat();
        labrador.bark();
    }
}

class AnimalTest {

    private final Set<String> executedMethods = new HashSet<>();

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        executedMethods.add("setUp");
        System.setOut(new PrintStream(outputStreamCaptor));
        executedMethods.clear();
    }

    @Test
    public void testDogEat() {
        executedMethods.add("testDogEat");
        Dog labrador = new Dog();
        labrador.eat();
        assertEquals("I can eat\nI eat dog food\n", outputStreamCaptor.toString().trim());
        assertTrue(executedMethods.contains("eat"));
    }

    @Test
    public void testDogBark() {
        executedMethods.add("testDogBark");
        Dog labrador = new Dog();
        labrador.bark();
        assertEquals("I can bark\n", outputStreamCaptor.toString().trim());
        assertTrue(executedMethods.contains("bark"));
    }

    @Test
    public void testCatEat() {
        executedMethods.add("testCatEat");
        Cat cat = new Cat();
        cat.eat();
        assertEquals("I can eat\nI eat cat food\n", outputStreamCaptor.toString().trim());
        assertTrue(executedMethods.contains("eat"));
    }

    @Test
    public void testHouseWithDog() {
        executedMethods.add("testHouseWithDog");
        House house = new House(new Dog());
        house.Dog();
        assertEquals("I can bark\n", outputStreamCaptor.toString().trim());
        assertTrue(executedMethods.contains("Dog"));
    }

    @Test
    public void testHouseWithNonDog() {
        executedMethods.add("testHouseWithNonDog");
        House house = new House(new Cat());
        house.Dog();
        assertEquals("There is no Dog", outputStreamCaptor.toString().trim());
        assertTrue(executedMethods.contains("Dog"));
    }

    @Test
    public void testMainMethod() {
        executedMethods.add("testMainMethod");
        MainExample.main(new String[0]);
        assertTrue(executedMethods.contains("main"));
        assertTrue(executedMethods.contains("eat"));
        assertTrue(executedMethods.contains("bark"));
    }
}
