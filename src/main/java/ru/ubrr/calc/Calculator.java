package ru.ubrr.calc;

//import org.slf4j.Logger;
//import static org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import java.security.KeyStore;
import java.util.*;

public class Calculator {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Calculator.class);
    static PriorityOperators priorityOperators = new PriorityOperators();
    private static List<LiteralEntity> arrayList;
    private int openBracket;
//    static List<LiteralEntity> brackets = new ArrayList<LiteralEntity>();

    static {
        priorityOperators.putPriority("+", 1);
        priorityOperators.putPriority("-", 1);
        priorityOperators.putPriority("*", 2);
        priorityOperators.putPriority("/", 2);
    }

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String s = scanner.nextLine().replace(" ", "");

            log.info("Ответ: {}", calc(s));
        }
    }

    public static String calc(String string){
        arrayList = new ArrayList<LiteralEntity>();
        StringIterator iterator = new StringIterator(string.replace(" ", ""));

        while (iterator.hasNext()) {

            arrayList.add(iterator.next());
        }
        return calculate(arrayList).getValue();
    }

    public static LiteralEntity calculate(List<LiteralEntity> literalEntityList) {
        /* todo вычисление результата */
        List<LiteralEntity> literalEntity = new ArrayList<LiteralEntity>(literalEntityList);
        List<LiteralEntity> brackets = new ArrayList<LiteralEntity>();

        if (literalEntity.size() <= 1)
            return literalEntity.get(0);

        brackets.addAll(nextbrackets(literalEntity));

        while (!brackets.isEmpty() && brackets.stream().filter((p) -> p.getType() == LiteralType.OPEN_BRACKET).count() > 0) {
            int first = literalEntity.indexOf(brackets.get(0));

            literalEntity.removeAll(brackets);
            literalEntity.add(first, calculate(brackets.subList(1, brackets.size() - 1)));

            brackets.clear();
            brackets.addAll(nextbrackets(literalEntity));
        }
        Deque<LiteralEntity> deque = new ArrayDeque<LiteralEntity>() {};
        deque.addAll(literalEntity);

        while (deque.size() > 1) {

            LiteralEntity number1 = deque.poll();
            LiteralEntity operator1 = deque.poll();
            LiteralEntity number2 = deque.poll();

            if (!deque.isEmpty() && priorityOperators.getPriority(operator1.getValue()) < priorityOperators.getPriority(deque.peek().getValue())) {
                LiteralEntity operator2 = deque.poll();
                LiteralEntity number3 = deque.poll();
                deque.addFirst(makeOperation(number2, number3, operator2));
                deque.addFirst(operator1);
                deque.addFirst(number1);
            } else
                deque.addFirst(makeOperation(number1, number2, operator1));
        }

        return deque.poll();

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

    public static List<LiteralEntity> nextbrackets(List<LiteralEntity> brackets) {
        int openBracket = -1;
        int closeBracket = -1;

        for (int i = 0; i < brackets.size(); i++) {
            if (brackets.get(i).getType() == LiteralType.OPEN_BRACKET) {
                openBracket = i;
            }
            if (brackets.get(i).getType() == LiteralType.CLOSE_BRACKET) {
                closeBracket = i;
                break;
            }
        }

        if (openBracket > -1 && closeBracket > -1)
            return brackets.subList(openBracket, ++closeBracket);

        return brackets;
    }


}
