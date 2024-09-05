package test;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class LFU implements CacheReplacementPolicy{
    private final LinkedHashMap<String,Integer> wordsMap;


    public LFU(){
        this.wordsMap = new LinkedHashMap<>();

    }

    @Override
    public void add(String w){
        int currentValue;
        if(wordsMap.containsKey(w)){  //If the word already exists, increments its value by 1.
            currentValue = wordsMap.get(w);
            wordsMap.put(w, currentValue+1);
        }
        else{
            wordsMap.put(w, 1); //dds a word that doesn't exist and initializes its value to 1
        }
    }

    @Override
    //In a LinkedHashMap, when checking for the minimum value, you will always get the word that was added first if multiple words have the same minimum value, because it preserves the insertion order.
    public String remove(){
        int leastUse = Integer.MAX_VALUE;
        String wordToRemove = "";
        for (HashMap.Entry<String, Integer> entry : wordsMap.entrySet()) {
            if(entry.getValue()< leastUse){
                leastUse = entry.getValue();
                wordToRemove = entry.getKey();
            }
        }
        return wordToRemove;
    }



}
