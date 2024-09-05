package test;

import java.util.HashMap;

public class DictionaryManager{
    private HashMap<String,test.Dictionary> booksMap;
    private static DictionaryManager singleDM = null;

    public DictionaryManager(){
        this.booksMap = new HashMap<>();

    }

    
    public boolean query(String ...args){
        String searchWord = args[args.length-1];
        String bookName;
        boolean found = false;
        Dictionary currentdDictionary;
        for (int i = 0; i <args.length-1 ; i++){        
            bookName = args[i];
            if(!booksMap.containsKey(bookName)){
                booksMap.put(bookName,new test.Dictionary(bookName));
            } 
            
            currentdDictionary = booksMap.get(bookName);
            if(currentdDictionary.query(searchWord)){
                found = true;
            }

        }
        return found;
    }

    public boolean challenge(String ...args){
        String searchWord = args[args.length-1];
        Dictionary currentdDictionary;
        boolean found=false;
        for (int i = 0; i <args.length-1 ; i++){
            currentdDictionary = booksMap.get(args[i]);
            if(currentdDictionary.challenge(searchWord)){
                found = true;
            }
        }
        return found;
    }

    public int getSize() {
        return booksMap.size();
    }

    public static DictionaryManager get(){
        if(DictionaryManager.singleDM==null){
            DictionaryManager.singleDM = new DictionaryManager();
        }
        return DictionaryManager.singleDM;
    }


}
