package ru.ubrr.calc;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@EqualsAndHashCode
@Getter
@AllArgsConstructor
@ToString
public class LiteralEntity {
    private int id;
    private String value;
    private LiteralType type;

    public LiteralEntity(){}
}
