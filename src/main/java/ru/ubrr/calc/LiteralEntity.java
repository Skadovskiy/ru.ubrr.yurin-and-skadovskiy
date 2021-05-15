package ru.ubrr.calc;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class LiteralEntity {
    private String value;
    private LiteralType type;

    public LiteralEntity(){}

    @Override
    public String toString() {
        return "LiteralEntity{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }

    public LiteralEntity(String value, LiteralType type) {
        this.value = value;
        this.type = type;
    }

}
