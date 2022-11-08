/**
 * Project 8
 * File: WordCounter2.java
 * Author: Zehra Gundogdu
 * Date: 4/20/2022
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;

public class WordCounter2 {

    private MapSet<String, Integer> map;
    private int totalWordCount;
    
    // WordCounter2 constructor
    public WordCounter2(String data_structure) {
        this.totalWordCount = 0;
        if(data_structure.equalsIgnoreCase("bst")) {
            this.map = new BSTMap<>(new AscendingString());
        } else if(data_structure.equalsIgnoreCase("hashmap")) {
            this.map = new HashMap<>(new AscendingString());
        }
        this.totalWordCount = 0;
    }

    //reads the contents of a txt file
    public ArrayList<String> readWords(String filename) {

        this.totalWordCount = 0;
        ArrayList<String> wordList = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            while(line != null) {
             
                String[] words = line.split("[^a-zA-Z0-9']");

                for (int i = 0; i < words.length; i++) {
                    String word = words[i].trim().toLowerCase();
                    if(word.length() > 0) {
                        wordList.add(word);
                        this.totalWordCount += 1;
                    }
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException ex) { 
            System.out.println("WordCounter.analyze():: unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("WordCounter.analyze():: error reading file " + filename);
        }

        return wordList;
    }

    //builds a map from the arraylist holding words
    public double buildMap(ArrayList<String> words) {
        this.clearMap();
        long startTime = System.nanoTime();
        for(String word: words) {

            if(this.map.containsKey(word)) {
                this.map.put(word, this.map.get(word) + 1);
            } else {
                this.map.put(word, 1);
            }
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        return (double) duration / (1000000);
    }

    //clears the map
    public void clearMap() {
        this.map.clear();
    }

    //returns the unique word count
    public int uniqueWordCount() {
        return this.map.size();
    }

    //returns the total word count
    public int totalWordCount() {
        return this.totalWordCount;
    }

    //getter method for how mnay times a word 
    public int getCount(String word) {
        return this.map.get(word);
    }

    //returns the frequency of the word 
    public double getFrequency(String word) {
        return (double)(this.getCount(word) / this.totalWordCount());
    }

    //writes the word count file
    public boolean writeWordCount(String filename) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write("totalWordCount : " + this.totalWordCount + "\n");
            ArrayList<KeyValuePair<String, Integer>> entrySet = this.map.entrySet();
            for(KeyValuePair<String, Integer> entry : entrySet) {
                myWriter.write(entry.getKey() + " " + entry.getValue() + "\n");
            }
            myWriter.close();
        } catch(IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    //reads the word count file
    public boolean readWordCount(String file) {
        this.map.clear();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String firstLine = bufferedReader.readLine();
            String[] firstLineSplit = firstLine.split(" : ");
            this.totalWordCount = Integer.parseInt(firstLineSplit[1]);

            String line = bufferedReader.readLine();

            while(line != null) {

                String[] keyValuePair = line.split(" ");
                this.map.put(keyValuePair[0], Integer.parseInt(keyValuePair[1]));
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("WordCounter2.analyze():: unable to open file " + file);
            return false;
        } catch (IOException ex) {
            System.out.println("WordCounter2.analyze():: error reading file " + file);
            return false;
        }

        return true;
    }

    //returns the number of collisions
    public int getNumCollisions() {
        if(this.map instanceof HashMap) {
            HashMap<String, Integer> hashMap = (HashMap<String, Integer>) this.map;
            return hashMap.getNumCollisions();
        }
        return 0;
    }

    //returns the depth of BST
    public int getDepth() {
        if(this.map instanceof BSTMap) {
            BSTMap<String, Integer> bstMap = (BSTMap<String, Integer>) this.map;
            return bstMap.getDepth();
        }
        return 0;
    }
    public static void main(String[] args) {

        //Takes the file names from the command line

        WordCounter2 wordCounter2 = new WordCounter2("hashmap");
        //WordCounter2 wordCounter2 = new WordCounter2("bst");

        ArrayList<String> wordList = wordCounter2.readWords(args[0]);
        double totalRunTime = 0;
        double firstTime = wordCounter2.buildMap(wordList);
        double maxTime = firstTime;
        double minTime = firstTime;
        totalRunTime += firstTime;

        for(int i = 1; i <= 4; i++) {
            System.out.println(i);
            double time = wordCounter2.buildMap(wordList);
            if (time > maxTime) {
                maxTime = time;
            }
            if (time < minTime) {
                minTime = time;
            }
            totalRunTime += time;
        }

        totalRunTime -= maxTime;
        totalRunTime -= minTime;
        double average = totalRunTime / 3.0d;

        System.out.println("Robust average of runtime = " + average);
        //System.out.println("Total number of words = " + wordCounter2.totalWordCount());
        //System.out.println("Unique number of words = " + wordCounter2.uniqueWordCount());

        System.out.println("Number of collisions = " + wordCounter2.getNumCollisions());
        //System.out.println("Depth of BST = " + wordCounter2.getDepth());

    }

}
