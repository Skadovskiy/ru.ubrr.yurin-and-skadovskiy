package ru.ubrr.calc;

import java.util.Iterator;

import static ru.ubrr.calc.LiteralType.*;

public class StringIterator implements Iterator<LiteralEntity> {

    private final String string;

    private Integer currentIndex;

    StringIterator(String string) {
        this.string = string;
        currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return string != null && currentIndex < string.length();
    }

    /**
     * @return сущность LiteralEntity
     */
    @Override
    public LiteralEntity next() {
        LiteralEntity res = null;
        var result = new StringBuilder();
        Character character = string.charAt(currentIndex);

        while (Character.isDigit(character) || character == '.' || (currentIndex == 0 && character == '-') ||
                (currentIndex > 0 && string.charAt(currentIndex - 1) == '(' && character == '-')
        ) {
            result.append(character);
            currentIndex++;

            if (currentIndex == string.length()) {
                res = new LiteralEntity(currentIndex, result.toString(), NUMBERS);

                break;
            }

            character = string.charAt(currentIndex);
        }
        if (res == null) {
            if (result.length() != 0) {
                res = new LiteralEntity(currentIndex, result.toString(), NUMBERS);
            } else {
                currentIndex++;
                res = switch (character) {
                    case '+', '-', '*', '/' -> new LiteralEntity(currentIndex, character.toString(), OPERATORS);
                    case '(' -> new LiteralEntity(currentIndex, character.toString(), OPEN_BRACKET);
                    case ')' -> new LiteralEntity(currentIndex, character.toString(), CLOSE_BRACKET);
                    default -> new LiteralEntity();
                };
            }

        }

        return res;
    }
}
