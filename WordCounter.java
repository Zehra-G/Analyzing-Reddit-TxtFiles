/**
 * Project 7
 * File: WordCounter.java
 * Author: Zehra Gundogdu
 * Date: 4/5/2022
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

public class WordCounter {

    //field to hold the BSTMap with types <String, Integer>
    public BSTMap<String, Integer> bst;
    // field to hold the total word count
    private int totalWordCount;

    public WordCounter(){
        totalWordCount = 0;
        bst = new BSTMap<String, Integer>(new AscendingString());
    }

    //generates the word counts from a file of words.
    //uses the BufferedReader to read in the file one line at a time
    //uses the String's split method to separate each line into words
    public void analyze(String filename){
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line = br.readLine();
                while (line != null) {
                    // split line into words. The regular expression can be interpreted
                    // as split on anything that is not (^) (a-z or A-Z or 0-9 or ').
                    String[] words = line.split("[^a-zA-Z0-9']+");
                    for (String word : words) {
                        System.out.println(word + ", " + word.length());
                        if (word.length() > 0) {
                            totalWordCount++;
                            if (bst.containsKey(word)) {
                                bst.put(word, bst.get(word) + 1);
                            } else {
                                bst.put(word, 1);
                            }
                        }
                    }
                    line = br.readLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    //returns the total word count (the total number of words in the document that was originally read in)
    public int getTotalWordCount(){
        return totalWordCount;
    }

    //returns the number of unique words, which is the size of the BSTMap
    public int getUniqueWordCount(){
        return bst.size();
    }

    //returns the frequency value associated with this word
    public int getCount(String word){
        Integer val = bst.get(word);
        if (val == null){
            return 0;
        }
        return val;
    }

    //returns the value associated with this word divided by the total word count
    public double getFrequency(String word){
        return (double) getCount(word) / totalWordCount;
    }

    //writes the contents of the BSTMap to a word count file
    /*
    On the first line of the file, the method writes the string "totalWordCount : " and the value of the total word count. 
    For each subsequent line, the method writes each key value pair separated by spaces
    */ 
    public void writeWordCountFile( String filename ){
        try {
            FileWriter fw = new FileWriter(filename);
            fw.write("totalWordCount : " + totalWordCount + "\n");
            for (String key : bst.keySet()) {
                fw.write(key + " " + bst.get(key) + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    //reads the contents of a word count file and reconstructs the fields of the WordCount object, including the BSTMap.
    public void readWordCountFile( String filename ){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            String[] words = line.split(" : ");
            totalWordCount = Integer.parseInt(words[1]);
            line = br.readLine();
            while (line != null) {
                words = line.split(" ");
                bst.put(words[0], Integer.parseInt(words[1]));
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    public String toString() {
        return bst.toString();
    }

    public static void main(String[] args){
        // In your main method, create a WordCounter object and call analyze on the file
        // "countest.txt".
        //Update the WordCounter main method be able to take the input and/or output filenames from the command line arguments

        WordCounter wc = new WordCounter();
        
        System.out.println("Start: 0ms");
        long startTime = System.currentTimeMillis();
        wc.analyze(args[0]);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("End: " + duration + " ms");
        System.out.println(wc.toString());
        System.out.println("Total Word Count: " + wc.getTotalWordCount());
        System.out.println("Unique Word Count: " + wc.getUniqueWordCount());
        
        //System.out.println("Frequency of the word 'the': " + wc.getFrequency("the"));
        //System.out.println("Frequency of the word 'and': " + wc.getFrequency("and"));
    
        // Test code for analyze, writeWordCount and readWordCount methods

        /*
        WordCounter wc = new WordCounter();
        wc.analyze("counttest.txt");
        wc.writeWordCountFile("counts_ct.txt");
        wc.readWordCountFile("counts_ct.txt");
        wc.writeWordCountFile("counts_ct_v2.txt"); 
        */

    }
    
}
