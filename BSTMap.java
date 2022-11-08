/*
Zehra Gundogdu

Template for the BSTMap classes
Fall 2020
CS 231 Project 6
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class BSTMap<K, V> implements MapSet<K, V>{

    private TNode <K,V> root;
    private Comparator<K> comp;
    private ArrayList<KeyValuePair<K, V>> pairList;
 

	// constructor: takes in a Comparator object
	public BSTMap( Comparator<K> comp ) {
        this.comp = comp;
	}

	// adds or updates a key-value pair
	// If there is already a pair with new_key in the map, then update
	// the pair's value to new_value.
	// If there is not already a pair with new_key, then
	// add pair with new_key and new_value.
	// returns the old value or null if no old value existed
	public V put( K key, V value ) {
        if (root == null){
            root = new TNode(key, value);
            return null;
        }

        return root.put(key, value, comp);
     
    }

    // gets the value at the specified key or null
    public V get( K key ) {
            // check for and handle the special case
            // call the root node's get method

        if (this.root == null){
            return null;
        }
        return root.get(key,comp);
    }

    // Write stubs (functions with no code) for the remaining
    // functions in the MapSet interface


	// entrySet notes: For the sake of the word-counting project, the
    // pairs should be added to the list by a pre-order traversal.



    // You can make this a nested class
    private class TNode <K, V> {
            // need fields for the left and right children
            private TNode<K, V> left;
            private TNode<K, V> right;
            private KeyValuePair<K, V> pair;
            // need a KeyValuePair to hold the data at this node

            // constructor, given a key and a value
            public TNode( K k, V v ) {
                pair = new KeyValuePair<K,V>(k, v);
                this.left = null;
                this.right = null;
            }

            // Takes in a key, a value, and a comparator and inserts
            // the TNode in the subtree rooted at this node 

			// Returns the value associated with the key in the subtree
			// rooted at this node or null if the key does not already exist
            public V put( K key, V value, Comparator<K> comp ) {
                if (comp.compare(key, pair.getKey()) == 0) {
                    V oldValue = pair.getValue();
                    pair.setValue(value);
                    return oldValue;
                }
                if (comp.compare(key, pair.getKey()) < 0) {
                    if(this.left== null) {
                        this.left= new TNode<K, V> (key, value);    
                        return null;      
                    } return this.left.put(key, value, comp);
                }
                if(this.right== null) {
                    this.right= new TNode<K, V> (key, value); 
                    return null;         
                } return this.right.put(key, value, comp);
            }

            // Takes in a key and a comparator
            // Returns the value associated with the key or null
            public V get( K key, Comparator<K> comp ) {
                if (comp.compare(key, pair.getKey()) == 0) {
                    return pair.getValue();
                }
                if (comp.compare(key, pair.getKey()) < 0) {
                    if(this.left== null) {
                        return null;
                    }
                    return this.left.get(key, comp);
                }

                if (comp.compare(key, pair.getKey()) > 0) {
                    if(this.right== null) {
                    return null;
                    }
                    return this.right.get(key, comp);
                }

                return null;
            }

            // Any additional methods you want to add below, for
            // example, for building ArrayLists
            
            
    }// end of TNode class

        public boolean containsKey(K key) {
            return get(key) != null;
        }

        public int getCount(TNode root){
            if(root == null){
                return 0;
            } 
            return 1 + getCount(root.right) + getCount(root.left);
        }
    
        public int size() {
            return this.getCount(this.root);
        }
    
        public ArrayList<K> keySet() {
            ArrayList<K> keys = new ArrayList<K>();
            Queue<TNode<K, V>> queue = new LinkedList<TNode<K, V>>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TNode<K, V> node = queue.remove();
                if (node != null) {
                    keys.add(node.pair.getKey());
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
            return keys;
        }

    @Override
    public void clear() {
        this.root = null;
    }

    // Travering the tree pre-order
    private void preorderRecursive(TNode rootNode) {
        if(rootNode != null) {
            pairList.add(rootNode.pair);
            preorderRecursive(rootNode.left);
            preorderRecursive(rootNode.right);
        }
    }

    //getter method for the depth of BST
    public int getDepth() {
        return this.maxDepthRecursive(this.root);
    }

    private int maxDepthRecursive(TNode node) {
        if(node == null) {
            return 0;
        }

        else {
            int leftDepth = this.maxDepthRecursive(node.left);
            int rightDepth = this.maxDepthRecursive(node.left);

            if(leftDepth > rightDepth) {
                return leftDepth + 1;
            } else {
                return rightDepth + 1;
            }
        }
    }

    //getter method for the entrySet for the BST
    @Override
    public ArrayList<KeyValuePair<K, V>> entrySet() {
        pairList = new ArrayList<KeyValuePair<K, V>>();
        preorderRecursive(this.root);
        return this.pairList;
    }
    public static void main( String[] argv ) {
            // create a BSTMap
            BSTMap<String, Integer> bst = new BSTMap<String, Integer>( new AscendingString() );

            bst.put( "twenty", 20 );
            bst.put( "ten", 10 );
            bst.put( "eleven", 11 );
            bst.put( "five", 5 );
            bst.put( "six", 6 );

            System.out.println( bst.get( "eleven" ) );

            // put more test code here

            
    }

}