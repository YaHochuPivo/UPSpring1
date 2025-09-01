package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/calculator")
    public String calculator() {
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam double num1, 
                          @RequestParam double num2, 
                          @RequestParam String operation, 
                          Model model) {
        
        double result = 0;
        
        if (operation.equals("add")) {
            result = num1 + num2;
        }
        if (operation.equals("subtract")) {
            result = num1 - num2;
        }
        if (operation.equals("multiply")) {
            result = num1 * num2;
        }
        if (operation.equals("divide")) {
            if (num2 != 0) {
                result = num1 / num2;
            } else {
                model.addAttribute("error", "Деление на ноль невозможно!");
                return "calculator";
            }
        }
        
        model.addAttribute("result", result);
        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("operation", operation);
        
        return "result";
    }

    @GetMapping("/converter")
    public String converter() {
        return "converter";
    }

    @PostMapping("/convert")
    public String convert(@RequestParam String fromCurrency, 
                         @RequestParam String toCurrency, 
                         @RequestParam double amount, 
                         Model model) {
        
        double rate = 1.0;
        
        if (fromCurrency.equals("USD") && toCurrency.equals("EUR")) {
            rate = 0.85;
        }
        if (fromCurrency.equals("EUR") && toCurrency.equals("USD")) {
            rate = 1.18;
        }
        if (fromCurrency.equals("USD") && toCurrency.equals("RUB")) {
            rate = 80.43;
        }
        if (fromCurrency.equals("RUB") && toCurrency.equals("USD")) {
            rate = 0.013;
        }
        if (fromCurrency.equals("EUR") && toCurrency.equals("RUB")) {
            rate = 94.0;
        }
        if (fromCurrency.equals("RUB") && toCurrency.equals("EUR")) {
            rate = 0.011;
        }
        
        double convertedAmount = amount * rate;
        
        model.addAttribute("fromCurrency", fromCurrency);
        model.addAttribute("toCurrency", toCurrency);
        model.addAttribute("amount", amount);
        model.addAttribute("convertedAmount", convertedAmount);
        model.addAttribute("rate", rate);
        
        return "converter";
    }
} 