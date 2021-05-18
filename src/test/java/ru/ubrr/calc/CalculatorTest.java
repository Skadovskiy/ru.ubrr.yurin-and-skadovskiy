package ru.ubrr.calc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static ru.ubrr.calc.LiteralType.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    //@Mock
    //private ArithmeticLiterals arithmetics;// = new ArithmeticLiterals();
    //@Mock
    //private StringIterator iterator;

    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        field.set(null, newValue);
    }

    @Test
    @DisplayName("Тест факториала")
    void calclatedTest() {
        Calculator calculator = new Calculator();
        assertThat(calculator.calc("11.635!")).isEqualTo("112983.8311174165");
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
            "4^(1/2), 2.0",
    })
    void calclatedTest(String expression, String expectedResult) {
        Calculator calculator = new Calculator();
        assertThat(calculator.calc(calculator.calc(expression))).isEqualTo(expectedResult);
    }

    @Test
    void calclatedMocitoTest() throws Exception {
        Calculator calculator = new Calculator();
        var arithmeticLiterals = new ArithmeticLiterals();
        StringIterator iterator = mock(StringIterator.class);
        PriorityOperators priorityOperators = mock(PriorityOperators.class);

        when(priorityOperators.getPriority("+")).thenReturn(1);
        when(priorityOperators.getPriority("-")).thenReturn(1);
        when(priorityOperators.getPriority("*")).thenReturn(2);
        when(priorityOperators.getPriority("/")).thenReturn(2);
        when(priorityOperators.getPriority("^")).thenReturn(3);
        when(priorityOperators.getPriority("!")).thenReturn(4);

        setFinalStatic(Calculator.class.getDeclaredField("priorityOperators"), priorityOperators);

        when(iterator.hasNext())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(iterator.next())
                .thenReturn(new LiteralEntity(0, "4", NUMBERS))
                .thenReturn(new LiteralEntity(1, "^", OPERATORS))
                .thenReturn(new LiteralEntity(2, "(", OPEN_BRACKET))
                .thenReturn(new LiteralEntity(3, "1", NUMBERS))
                .thenReturn(new LiteralEntity(4, "/", OPERATORS))
                .thenReturn(new LiteralEntity(5, "2", NUMBERS))
                .thenReturn(new LiteralEntity(6, ")", CLOSE_BRACKET))
        ;

        while (iterator.hasNext()) {
            arithmeticLiterals.add(iterator.next());
        }

        assertThat(calculator.calculate(arithmeticLiterals)).isEqualTo(new LiteralEntity(0, "2.0", NUMBERS));
    }
}
