package org.example;

public class Laptop implements Computer {

    public Laptop() {
        System.out.println("laptop Constructor");
    }

    public void compile() {
        System.out.println("Compiling laptop");
    }
}
