package test;

import java.io.File;
import java.util.Scanner;


public class Dictionary {
    private  CacheManager cachWordsExist;
    private CacheManager cachWordsNotExist;
    private BloomFilter bf;
    private CacheReplacementPolicy LRU;
    private  CacheReplacementPolicy LFU;
    private String[] algs = {"SHA1","MD5"};
    private String[] filesName;


    public Dictionary(String ...filesNames){
        this.LRU = new LRU();
        this.LFU = new LFU();
        this.cachWordsExist = new CacheManager(400,this.LRU);
        this.cachWordsNotExist = new CacheManager(100,this.LFU);
        this.bf = new BloomFilter(256,this.algs);
        this.filesName = filesNames;

        for (String file : filesNames) {
            try {
                Scanner scanner = new Scanner(new File(file));
                while(scanner.hasNextLine()){
                    bf.add(scanner.next());
                }
            }
            catch (Exception e) {

            }

        }


    }

    public boolean query(String word){
        if (this.cachWordsExist.query(word)) {
            return true;
        }
        else{
            if (this.cachWordsNotExist.query(word)) {
                return false;
            }
            else{
                if(this.bf.contains(word)){
                    this.cachWordsExist.add(word);
                    return true;
                }

            }
        }

        return false;
    }

    public boolean challenge(String word){
        if(IOSearcher.search(word, this.filesName)){
            this.cachWordsExist.add(word);
            return true;
        }
        return false;
    }

}
