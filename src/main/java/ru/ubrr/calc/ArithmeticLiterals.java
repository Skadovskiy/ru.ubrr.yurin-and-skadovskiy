package ru.ubrr.calc;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ArithmeticLiterals {

    private static final Integer FIRST_ELEMENT = 0;

    private List<LiteralEntity> literalEntityList;

    public ArithmeticLiterals(){
        literalEntityList = new ArrayList<>();
    }
    public ArithmeticLiterals(List<LiteralEntity> arrayList){
        literalEntityList = new ArrayList<>(arrayList);
    }

    public ArithmeticLiterals(ArithmeticLiterals arithmeticLiterals){
        literalEntityList = new ArrayList<>(arithmeticLiterals.getLiteralEntityList());
    }

    public void add(LiteralEntity literalEntity){
        literalEntityList.add(literalEntity);
    }

    public void add(Integer index, LiteralEntity literalEntity){
        literalEntityList.add(index, literalEntity);
    }

    public Integer size(){
        return literalEntityList.size();
    }

    public LiteralEntity get(Integer key){
        return literalEntityList.get(key);
    }

    public void addAll(ArithmeticLiterals arithmeticLiterals){
        this.getLiteralEntityList().addAll(arithmeticLiterals.getLiteralEntityList());
    }

    public ArithmeticLiterals nextBrackets() {
        int openBracket = -1;
        int closeBracket = -1;

        for (var i = 0; i < literalEntityList.size(); i++) {
            if (literalEntityList.get(i).getType() == LiteralType.OPEN_BRACKET) {
                openBracket = i;
            }
            if (literalEntityList.get(i).getType() == LiteralType.CLOSE_BRACKET) {
                closeBracket = i;
                break;
            }
        }

        if (openBracket > -1 && closeBracket > -1)
            return new ArithmeticLiterals(literalEntityList.subList(openBracket, ++closeBracket));

        return this;
    }

    public boolean isEmpty(){
        return literalEntityList.isEmpty();
    }

    public Integer indexOf(LiteralEntity literalEntity){
        return literalEntityList.indexOf(literalEntity);
    }

    public LiteralEntity getFirstElement(){
        return literalEntityList.get(FIRST_ELEMENT);
    }

    public void removeAll(ArithmeticLiterals arithmeticLiterals){
        literalEntityList.removeAll(arithmeticLiterals.getLiteralEntityList());
    }

    public ArithmeticLiterals trimBrackets(){
        literalEntityList = literalEntityList.subList(1, literalEntityList.size() - 1);
        return this;
    }

    public void clear(){
        literalEntityList.clear();
    }
}
