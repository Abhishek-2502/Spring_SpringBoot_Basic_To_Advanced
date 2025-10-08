package com.example.calc.controller;

import com.example.calc.model.CalculatorRequest;
import com.example.calc.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody CalculatorRequest request) {
        try {
            // Validate input (optional, for example operation check)
            if (request.getOperation() == null || request.getOperation().isEmpty()) {
                return ResponseEntity.badRequest().body("Operation is required.");
            }

            // Perform the calculation
            CalculatorRequest result = calculatorService.calculate(
                    request.getNumber1(),
                    request.getNumber2(),
                    request.getOperation()
            );
            return ResponseEntity.ok(result);
        } catch (ArithmeticException e) {
            // Handle division by zero case
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CalculatorRequest(0, 0, e.getMessage(), 0));
        } catch (IllegalArgumentException e) {
            // Handle invalid operation
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CalculatorRequest(0, 0, e.getMessage(), 0));
        } catch (Exception e) {
            // Catch all other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CalculatorRequest(0, 0, "An unexpected error occurred.", 0));
        }
    }
}
