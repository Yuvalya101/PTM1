package test;

import java.util.HashSet;


public class CacheManager {
    private final HashSet<String> cacheWords = new HashSet<>();
    private final int size;
    private final CacheReplacementPolicy crp;

    public CacheManager(int size,CacheReplacementPolicy crp){
        this.size= size;
        this.crp= crp;
    }


    public boolean query(String w){
        return cacheWords.contains(w);
    }

    public void add(String w){
        int currentSizeOfCache;
        String removeWord;
        crp.add(w);
        cacheWords.add(w);
        currentSizeOfCache = cacheWords.size();
        if(currentSizeOfCache>this.size){
            removeWord = crp.remove();
            if(removeWord!=null){
                cacheWords.remove(removeWord);
            }
        }
    }

}
