package test;

import java.math.BigInteger;
import java.util.BitSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BloomFilter {
    private int size;
    private BitSet bits;
    private MessageDigest [] hashFunc;

    public BloomFilter(int size, String ... algs){
        this.size = size;
        this.bits = new BitSet(size);
        this.hashFunc = new MessageDigest[algs.length];
        int i = 0;
        for(String alg : algs){
            try{
                hashFunc[i] = MessageDigest.getInstance(alg);
                i++;
            }
            catch(NoSuchAlgorithmException e){
                throw new RuntimeException("No such algs");
            }
        }
    }

    public int getWordBit(String word, MessageDigest hashFunction){
        byte [] bts = hashFunction.digest(word.getBytes());
        BigInteger bi = new BigInteger(bts);
        return Math.abs(bi.intValue()) % size;
    }
    public void add(String word) {
        for (MessageDigest hf : this.hashFunc)
            this.bits.set(getWordBit(word, hf));
    }
    public boolean contains(String word){
        for(MessageDigest hf: this.hashFunc){
            if(!this.bits.get(getWordBit(word,hf)))
                return false;
        }
        return true;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i< this.bits.length(); i++){
            if(this.bits.get(i))
                sb.append("1");
            else
                sb.append("0");
        }
        return sb.toString();
    }
}
