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

    public static LiteralEntity calculate(List<LiteralEntity> literalEntity) {
        /* todo вычисление результата */
        List<LiteralEntity> brackets = new ArrayList<LiteralEntity>();

        if (literalEntity.size() <= 1)
            return literalEntity.get(0);


        Pair pair = nextbrackets(literalEntity);
        brackets.addAll(pair.getLiteralEntityList());

//        if (brackets.size() == 3)
//            return brackets.get(1);


        while (!brackets.isEmpty() && brackets.stream().filter((p) -> p.getType() == LiteralType.OPEN_BRACKET).count() > 0) {
            //int first = literalEntity.indexOf(brackets.get(0)); ///replaceAll((brackets, ) -> {calculate(brackets)});
            int first = pair.getId();

            literalEntity.removeAll(brackets);

            literalEntity.add(first, calculate(brackets.subList(1, brackets.size() - 1)));
            //literalEntity.add(first, calculate(brackets));

            brackets.clear();
            pair = nextbrackets(literalEntity);
            brackets.addAll(pair.getLiteralEntityList());
        }
        Deque<LiteralEntity> deque = new ArrayDeque<LiteralEntity>() {};
        //stack.addAll(literalEntity);
        deque.addAll(literalEntity);

        while (deque.size() > 1) {

            //2 + ((3 * 4) - 3) (3 * 4)
            //2 + 12
            //2,+,3,*,4 -->> 2,+,12

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

        /*while (stack.size() > 1) {

            //2 + ((3 * 4) - 3) (3 * 4)
            //2 + 12
            //2,+,3,*,4 -->> 2,+,12

            LiteralEntity number1 = stack.pop();
            LiteralEntity operator1 = stack.pop();
            LiteralEntity number2 = stack.pop();

            if (!stack.isEmpty() && priorityOperators.getPriority(operator1.getValue()) < priorityOperators.getPriority(stack.peek().getValue())) {
                LiteralEntity operator2 = stack.pop();
                LiteralEntity number3 = stack.pop();
                stack.push(makeOperation(number2, number3, operator2));
                stack.push(operator1);
                stack.push(number1);
            } else
                stack.push(makeOperation(number1, number2, operator1));
        }

        return stack.pop();*/
    }

    public static LiteralEntity makeOperation(LiteralEntity first, LiteralEntity second, LiteralEntity operator) {
        return new LiteralEntity(String.valueOf(switch (operator.getValue()) {
            case "+" -> Double.valueOf(first.getValue()) + Double.valueOf(second.getValue());
            case "-" -> Double.valueOf(first.getValue()) - Double.valueOf(second.getValue());
            case "*" -> Double.valueOf(first.getValue()) * Double.valueOf(second.getValue());
            case "/" -> Double.valueOf(first.getValue()) / Double.valueOf(second.getValue());
            default -> 0d;
        }), LiteralType.NUMBERS);
    }

    public static Pair nextbrackets(List<LiteralEntity> brackets) {
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
            return new Pair (openBracket, brackets.subList(openBracket, ++closeBracket));

        return new Pair (openBracket, brackets);
    }

}
