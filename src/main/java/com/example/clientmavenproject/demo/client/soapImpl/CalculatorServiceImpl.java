package com.example.clientmavenproject.demo.client.soapImpl;

import com.example.clientmavenproject.demo.client.CalculatorService;
import com.example.clientmavenproject.demo.generated.math.*;

public class CalculatorServiceImpl extends BaseServiceImpl implements CalculatorService {
    @Override
    public Integer add(Integer a, Integer b) {
        Add add = new Add();
        add.setIntA(a);
        add.setIntB(b);
        AddResponse response = (AddResponse) callWebService(add);
        return response.getAddResult();
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        Subtract subtract = new Subtract();
        subtract.setIntA(a);
        subtract.setIntB(b);
        SubtractResponse response = (SubtractResponse) callWebService(subtract);
        return response.getSubtractResult();
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        Multiply multiply = new Multiply();
        multiply.setIntA(a);
        multiply.setIntB(b);
        MultiplyResponse response = (MultiplyResponse) callWebService(multiply);
        return response.getMultiplyResult();
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        Divide divide = new Divide();
        divide.setIntA(a);
        divide.setIntB(b);
        DivideResponse response = (DivideResponse) callWebService(divide);
        return response.getDivideResult();
    }
}
