package ru.ubrr.calc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import org.junit.jupiter.api.AssertEquals;

public class CalculatorTest {

    @Test
    @DisplayName("10 - (2 * (2 / 2 + 1)) = 6.0")
    void CalclatedTest(){
        assertEquals("6.0", (Calculator.calc("10 - (2 * (2 / 2 + 1))")));
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
            //"abc, NaN"
            "3 * 4 - 2 + 20 / 4 + 1 * 100/96 * (2- 1), 16.041666666666668",
            "(3 - ((2 * 2))),   -1.0,",
    })
    void add(String expression, String expectedResult) {
        assertEquals(expectedResult, Calculator.calc(expression));
    }
}
