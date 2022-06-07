package uc.seng301.wordleapp.assignment5.guesser;

import java.util.ArrayList;
import java.util.List;

public class CareTaker{
    private List<Memento> mementoList = new ArrayList<>();

    public int getMementoListSize (){
        return mementoList.size();
    }

    public void subListMementoList(int fromIndex, int toIndex){
        mementoList.subList(fromIndex, toIndex).clear();
    }

    public void add(Memento memento, int numGuesses){
        // Caretakers destroy obsolete mementos before add new memento
        int mementoListSize = getMementoListSize();
        if (numGuesses < getMementoListSize()){
            subListMementoList(numGuesses, mementoListSize);        }
        mementoList.add(memento);
    }

    public Memento getMementoByNum(int numGuesses){
        return mementoList.stream().filter(mmt -> mmt.getNumGuesses() == numGuesses).findFirst().orElse(null);
    }
}
