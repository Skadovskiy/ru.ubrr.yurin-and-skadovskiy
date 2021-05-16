package ru.ubrr.calc;

import org.slf4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Scanner;

public class Calculator {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Calculator.class);
    private static PriorityOperators priorityOperators = new PriorityOperators();

    static {
        priorityOperators.putPriority("+", 1);
        priorityOperators.putPriority("-", 1);
        priorityOperators.putPriority("*", 2);
        priorityOperators.putPriority("/", 2);
    }

    public static void main(String... args) {
        var calculator = new Calculator();
        var scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String s = scanner.nextLine().replace(" ", "");

            log.info("Ответ: {}", calculator.calc(s));
        }
    }

    public static LiteralEntity makeOperation(LiteralEntity first, LiteralEntity second, LiteralEntity operator) {
        return new LiteralEntity(0, String.valueOf(switch (operator.getValue()) {
            case "+" -> Double.valueOf(first.getValue()) + Double.valueOf(second.getValue());
            case "-" -> Double.valueOf(first.getValue()) - Double.valueOf(second.getValue());
            case "*" -> Double.valueOf(first.getValue()) * Double.valueOf(second.getValue());
            case "/" -> Double.valueOf(first.getValue()) / Double.valueOf(second.getValue());
            default -> 0d;
        }), LiteralType.NUMBERS);
    }

    public String calc(String string) {
        var arithmeticLiterals = new ArithmeticLiterals();
        var iterator = new StringIterator(string.replace(" ", ""));

        while (iterator.hasNext()) {
            arithmeticLiterals.add(iterator.next());
        }
        return calculate(arithmeticLiterals).getValue();
    }

    public LiteralEntity calculate(ArithmeticLiterals literalEntityList) {
        var literalEntity = new ArithmeticLiterals(literalEntityList);
        var brackets = new ArithmeticLiterals();

        if (literalEntity.size() <= 1)
            return literalEntity.get(0);

        brackets.addAll(literalEntity.nextBrackets());

        while (!brackets.isEmpty() && brackets.getLiteralEntityList().stream().anyMatch((p) -> p.getType() == LiteralType.OPEN_BRACKET)) {
            int first = literalEntity.indexOf(brackets.getFirstElement());

            literalEntity.removeAll(brackets);
            literalEntity.add(first, calculate(brackets.trimBrackets()));

            brackets.clear();
            brackets.addAll(literalEntity.nextBrackets());
        }

        return calculeteSimpleOperation(literalEntity);
    }

    public LiteralEntity calculeteSimpleOperation(ArithmeticLiterals arithmeticLiterals) {
        Deque<LiteralEntity> deque = new ArrayDeque<>() {
        };
        deque.addAll(arithmeticLiterals.getLiteralEntityList());

        while (deque.size() > 1) {
            LiteralEntity firstNumber = deque.poll();
            LiteralEntity firstOperator = deque.poll();
            LiteralEntity secondNumber = deque.poll();

            if (!deque.isEmpty() && priorityOperators.getPriority(Objects.requireNonNull(firstOperator).getValue()) <
                    priorityOperators.getPriority(Objects.requireNonNull(deque.peek()).getValue())) {
                LiteralEntity secondOperator = deque.poll();
                LiteralEntity thirdNumber = deque.poll();
                deque.addFirst(makeOperation(secondNumber, thirdNumber, secondOperator));
                deque.addFirst(firstOperator);
                deque.addFirst(firstNumber);
            } else
                deque.addFirst(makeOperation(firstNumber, secondNumber, firstOperator));
        }

        return deque.poll();
    }
}
