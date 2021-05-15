package ru.ubrr.calc;

import java.util.Iterator;
import java.util.function.Consumer;

import static ru.ubrr.calc.LiteralType.*;

public class StringIterator implements Iterator<LiteralEntity> {

    private String string;

    private Integer currentIndex;

    StringIterator(String string) {
        this.string = string;
        currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return string != null && currentIndex < string.length();
    }

    @Override
    public LiteralEntity next() {
        StringBuilder result = new StringBuilder();
        Character character = string.charAt(currentIndex);

        while (Character.isDigit(character) || character == '.' || (currentIndex == 0 && character == '-') ||
                (currentIndex > 0 && string.charAt(currentIndex - 1) == '(' && character == '-')
        ) {
            result.append(character);
            currentIndex++;

            if (currentIndex == string.length())
                return new LiteralEntity(result.toString(), NUMBERS);

            character = string.charAt(currentIndex);
        }

        if (result.length() != 0)
            return new LiteralEntity(result.toString(), NUMBERS);

        currentIndex++;
        return switch (character) {
            case '+', '-', '*', '/' -> new LiteralEntity(character.toString(), OPERATORS);
            case '(' -> new LiteralEntity(character.toString(), OPEN_BRACKET);
            case ')' -> new LiteralEntity(character.toString(), CLOSE_BRACKET);
            default -> new LiteralEntity();
        };
    }
}
