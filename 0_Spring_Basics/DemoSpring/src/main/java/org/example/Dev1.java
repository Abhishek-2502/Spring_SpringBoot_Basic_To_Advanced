package org.example;

public class Dev1 {

    private Laptop laptop;

    int age;
    int age1 = 10;
    int age2;

    private int age3;

    public int getAge3() {
        return age3;
    }

    public void setAge3(int age3) {
        this.age3 = age3;
    }

    private int age4;

    public int getAge4() {
        return age4;
    }

    public void setAge4(int age4) {
        this.age4 = age4;
    }

    public Dev1() {
        System.out.println("Dev1 Constructor");
    }

    public void build() {
        System.out.println("Working with Spring without Boot Dev1");
        laptop.compile();
    }
}
