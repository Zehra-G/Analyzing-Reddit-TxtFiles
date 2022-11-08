/**
 * Lab 6
 * File: KeyValuePair.java
 * Author: Zehra Gundogdu
 * Date: 4/5/2022
 */

public class KeyValuePair <K, V>{
    private K key;
    private V val;

    //the constructor initializing the key and value fields.
    public KeyValuePair(K k, V v){
        key = k;
        val = v;
    }

    //returns the Key
    public K getKey(){
        return key;
    }

    //returns the value
    public V getValue(){
        return val;
    }

    //sets the value
    public void setValue( V v){
        val = v;
    }

    //returns a String containing both the key and value.
    public String toString(){
        return "Key: " + this.key + " Value: " + this.val;
    }

    public static void main(String[] args){

        KeyValuePair pair = new KeyValuePair(3, 2);
        System.out.println("Key = " + pair.getKey());
        System.out.println("Value = " + pair.getValue());
        pair.setValue(5);
        System.out.println(pair);
    }
}