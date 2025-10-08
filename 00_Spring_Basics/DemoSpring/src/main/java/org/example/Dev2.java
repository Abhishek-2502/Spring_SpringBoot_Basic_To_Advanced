package org.example;

public class Dev2 {

    private Laptop laptop;
    private int age;

    //    Constructors
    public Dev2() {
        System.out.println("Dev2 Constructor");
    }

    public Dev2(int age) {
        this.age = age;
        System.out.println("Dev2 age params Constructor");
    }

    public Dev2(Laptop laptop) {
        this.laptop = laptop;
        System.out.println("Dev2 Laptop params Constructor");
    }

    //    Getter Setter
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public void build() {
        System.out.println("Working with Spring without Boot Dev2");
        laptop.compile();
    }
}
