package com.example.calc.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculatorRequest {
    private double number1;
    private double number2;
    private String operation;
    private double result;
}
