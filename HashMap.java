/**
 * Lab 8
 * File: HashMap.java
 * Author: Zehra Gundogdu
 * Date: 4/20/2022
 */

import java.util.ArrayList;
import java.util.Comparator;

public class HashMap<K, V> implements MapSet<K, V> {

    private HashNode<K, V>[] hashTable = null;
    private Comparator<K> comp;
    private int capacity;
    private int filled;
    private int numCollisions;

    //default hashmap constructor
    public HashMap(Comparator<K> incomp) {
        this.comp = incomp;
        this.capacity = 16;
        this.filled = 0;
        this.hashTable = (HashNode<K, V>[]) new HashNode[this.capacity];
        this.numCollisions = 0;
    }

    //hashmap constructor with specified capacity
    public HashMap(Comparator<K> incomp, int capacity) {
        this.comp = incomp;
        this.capacity = capacity;
        this.filled = 0;
        this.hashTable = (HashNode<K, V>[]) new HashNode[this.capacity];
        this.numCollisions = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % this.capacity;
    }

    public class HashNode<K, V> {
        private KeyValuePair<K, V> pair;
        private HashNode<K, V> next;
        private BSTMap<K, Integer> map;

        //constructor for the HashNode class
        public HashNode(K key, V value) {
            this.pair = new KeyValuePair<K,V>(key, value);
            this.next = null;
            this.map = new BSTMap(new AscendingString());
        }

        //getter method for the map
        public BSTMap<K, Integer> getMap() {
            return this.map;
        }

        //setter method for the map
        public void setMap(BSTMap<K, Integer> map) {
            this.map = map;
        }

        //setter method for the pairs
        public void setPair(KeyValuePair<K, V> pair) {
            this.pair = pair;
        }

        //getter method for the pairs
        public KeyValuePair<K, V> getPair() {
            return this.pair;
        }

        //setter method for the next node
        public void setNext(HashNode<K, V> next) {
            this.next = next;
        }

        //getter method for the next node 
        public HashNode<K, V> getNext() {
            return this.next;
        }

        //getter method for the key
        public K getKey() {
            return this.pair.getKey();
        }

        //getter method for the values
        public V getValue() {
            return this.pair.getValue();
        }

        //setter method for the values
        public void setValue(V v) {
            this.pair.setValue(v);
        }

    }

    @Override
    public V put(K key, V value) {

        if(this.filled > this.capacity) {
            ArrayList<KeyValuePair<K, V>> data = this.entrySet();
            HashNode<K, V>[] temp = (HashNode<K, V>[]) new HashNode[this.capacity * 2];
            this.capacity *= 2;
            this.hashTable = temp;
            this.filled = 0;
            this.numCollisions = 0;

            for(int i = 0; i < data.size(); i++) {
                this.put(data.get(i).getKey(), data.get(i).getValue());
            }
        }
        return this.linkedListChaining(key, value);
    }

    //chaining with a linkedlist implementation to handle collisions
    private V linkedListChaining(K key, V value) {
        int position = this.hash(key);
        HashNode<K, V> current = this.hashTable[position];
        V oldValue = null;
        if(current == null) {
            this.hashTable[position] = new HashNode<K, V>(key, value);
            this.filled += 1;
        } else {
            while(current.next != null && this.comp.compare(current.getKey(), key) != 0) {
                current = current.next;
            }
            if(this.comp.compare(current.getKey(), key) == 0) {
                current.setValue(value);
            } else {
                current.setNext(new HashNode<K, V>(key, value));
                this.filled += 1;
                this.numCollisions += 1;
            }
        }
        return oldValue;
    }

    /*
    Implementing the methods in the MapSet Interface
    */

    @Override
    public boolean containsKey(K key) {
        int position = this.hash(key);
        HashNode<K, V> current = this.hashTable[position];

        while(current != null && this.comp.compare(current.getKey(), key)  != 0) {
            current = current.getNext();
        }
        
        if(current == null) {
            return false;
        }

        if(this.comp.compare((K) current.getKey(), key) == 0) {
            return true;
        }
    
        return false;
    }

    @Override
    public V get(K key) {
        int position = this.hash(key);

        HashNode<K, V> current = this.hashTable[position];

        while(current != null && this.comp.compare(current.getKey(), key) != 0) {
            current = current.next;
        }

        if(current == null) {
            return null;
        }

        if(this.comp.compare(current.getKey(), key) == 0) {
            return current.getValue();
        }
        return null;
    }

    @Override
    public ArrayList<K> keySet() {
        ArrayList<KeyValuePair<K, V>> entrySet = this.entrySet();
        ArrayList<K> keys = new ArrayList<K>();
        for(KeyValuePair<K, V> pair : entrySet) {
            keys.add(pair.getKey());
        }
        return keys;
    }

    @Override
    public ArrayList<KeyValuePair<K, V>> entrySet() {

        ArrayList<KeyValuePair<K, V>> entrySet = new ArrayList<KeyValuePair<K, V>>();
        for(int i = 0; i < this.capacity; i++) {
            HashNode<K, V> current = this.hashTable[i];
            while(current != null) {
                entrySet.add(current.getPair());
                current = current.getNext();
            }
        }
        return entrySet;
    }

    @Override
    public int size() {
        return this.filled;
    }

    @Override
    public void clear() {
        this.numCollisions = 0;
        this.filled = 0;
        this.hashTable = (HashMap<K,V>.HashNode<K,V>[]) new HashNode[this.capacity];
    }

    //removes a node from the hashmap
    public boolean remove(K key) {

        int position = this.hash(key);

        HashNode<K, V> target = this.hashTable[position];

        HashNode<K, V> current = target;
        HashNode<K, V> previous = null;

        while(current != null && current.pair.getKey() != null) {
            previous = current;
            current = current.next;
        }

        if(current == null) {
            return false;
        }

        if(previous == null) {
            this.hashTable[position] = current.next;
        } else {
            previous.next = current.next;
        }

        this.filled -= 1;
        return true;

    }

    //finds a node in the hashmap
    public HashNode<K, V> find(K key) {
        int position = this.hash(key);

        HashNode<K, V> current = this.hashTable[position];

        while(current != null && this.comp.compare(current.getPair().getKey(),key) != 0) {
            current = current.next;
        }

        if(this.comp.compare(current.getPair().getKey(), key) == 0) {
            return current;
        }

        return null;

    }

    //returns the number of collisions
    public int getNumCollisions() {
        return this.numCollisions;
    }

    @Override
    public String toString() {
        String result = "";

        for(int i = 0; i < capacity; i++) {
            HashNode<K, V> current = this.hashTable[i];

            while(current != null) {
                result += current.getPair().getKey() + " = " + current.getPair().getValue() + ", ";
                current = current.next;
            }
        }
        return result;
    }
    public static void main(String[] args) {
        //Testing the methods

        HashMap<String, String> map = new HashMap<>(new AscendingString());
		map.put("one", "1");
		map.put("two", "2");
		System.out.println(map);
		System.out.println(map.containsKey("1"));
		System.out.println(map.containsKey("9"));
		System.out.println(map.get("2"));
		System.out.println(map.get("3"));
		System.out.println(map.find("one").getPair().getValue());
    }

}


