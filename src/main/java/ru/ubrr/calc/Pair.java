package ru.ubrr.calc;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Pair {
    int id;
    List<LiteralEntity> literalEntityList;
}
