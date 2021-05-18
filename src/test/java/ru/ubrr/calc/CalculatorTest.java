package ru.ubrr.calc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    @DisplayName("Тест факториала")
    void calclatedTest(){
        Calculator calculator = new Calculator();
        assertEquals("112983.8311174165", (calculator.calc("11.635!")));
    }

    @ParameterizedTest(name = "{0} = {1}")
    @CsvSource({
            "1 + 2,          3.0,",
            "-7 + 2,        -5.0,",
            "3 - (2 * 2),   -1.0,",
            "3 - (2 * 2) + (1 + 1), 1.0",
            "3 - (2 * 2 / 2 + 1), 0.0",
            "10 - (2 * (2 / 2 + 1)), 6.0",
            "3 * (4 - 2) * (4 + 1), 30.0",
            "3 * ((4 - 2) + (-20 / 4 + 1)), -6.0",
            "3 * 4 - 2 + 20 / 4 + 1 * 100/96 * (2- 1), 16.041666666666668",
            "(3 - ((2 * 2))),   -1.0,",
            "2 ^ 2,   4.0",
            "4^0.5,   2.0",
            "4^(1/2),   2.0",
            "11.635!,   112983.8311174165",
            "0!,   1.0",
            "3 * (2^(4^0.5 - 2) + (-20 / 0!)), -57.0",
    })
    void calclatedTest (String expression, String expectedResult) {
        Calculator calculator = new Calculator();
        assertEquals(expectedResult, calculator.calc(expression));
    }
}
