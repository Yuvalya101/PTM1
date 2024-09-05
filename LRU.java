package test;

import java.util.LinkedList;

public class LRU implements CacheReplacementPolicy {

    private final LinkedList<String> words;

    public LRU(){
        this.words = new LinkedList<String>();
    }

    @Override
    public void add(String w){
        if(words.contains(w)){
            words.remove(w);
            words.add(w);
        }
        else{
            words.add(w);
        }

    }

    @Override
    public String remove(){
        return words.removeFirst();
    }




}
