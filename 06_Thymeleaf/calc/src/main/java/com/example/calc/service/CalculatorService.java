package com.example.calc.service;

import com.example.calc.model.CalculatorRequest;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public CalculatorRequest calculate(double number1, double number2, String operation) {
        double result;

        if (operation.equalsIgnoreCase("add")) {
            result = number1 + number2;
        } else if (operation.equalsIgnoreCase("subtract")) {
            result = number1 - number2;
        } else if (operation.equalsIgnoreCase("multiply")) {
            result = number1 * number2;
        } else if (operation.equalsIgnoreCase("divide")) {
            if (number2 == 0) {
                throw new ArithmeticException("Division by zero is not allowed");
            }
            result = number1 / number2;
        } else {
            throw new IllegalArgumentException("Invalid operation");
        }

        return new CalculatorRequest(number1, number2, operation, result);
    }
}
