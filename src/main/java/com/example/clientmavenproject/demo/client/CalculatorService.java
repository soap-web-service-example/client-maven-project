package com.example.clientmavenproject.demo.client;

import com.example.clientmavenproject.demo.generated.math.Add;

public interface CalculatorService {
    Integer add(Integer a, Integer b);

    Integer subtract(Integer a, Integer b);

    Integer multiply(Integer a, Integer b);

    Integer divide(Integer a, Integer b);

}
