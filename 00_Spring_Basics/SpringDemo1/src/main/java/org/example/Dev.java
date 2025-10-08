package org.example;

public class Dev {

    private Computer comp;  // Object of interface is not allowed in Core Java, but allowed in Spring as Spring uses Dependency Injection

    //    Constructors
    public Dev() {
        System.out.println("Dev Constructor");
    }

    //    Getter Setter
    public Computer getComp() {
        return comp;
    }

    public void setComp(Computer comp) {
        this.comp = comp;
    }

    public void build() {
        System.out.println("Working with Spring without Boot Dev");
        comp.compile();
    }
}
