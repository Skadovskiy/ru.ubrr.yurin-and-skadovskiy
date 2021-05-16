package ru.ubrr.calc;

import java.util.HashMap;
import java.util.Map;

public class PriorityOperators {

    private final Map<String, Integer> map = new HashMap<>();

    public Integer getPriority(String key){
        return map.get(key);
    }

    public void putPriority(String key, Integer value){
        map.put(key, value);
    }
}
