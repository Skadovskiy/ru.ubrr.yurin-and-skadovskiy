package ru.ubrr.calc;

import java.util.HashMap;
import java.util.Map;

public class PriorityOperators {

    private Map<String, Integer> map;

    PriorityOperators(Map<String, Integer> map){
        this.map = map;
    }

    PriorityOperators(){
        map = new HashMap<String, Integer>();
    }

    public Integer getPriority(String key){
        return map.get(key);
    }

    public void putPriority(String key, Integer value){
        map.put(key, value);
    }
}
