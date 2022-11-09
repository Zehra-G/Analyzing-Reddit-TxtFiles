# Examining The Efficiency of Hashmap and BSTMap Implementations in Analyzing Text Files
## Abstract
In this project, I implemented a hash table and compared its efficiency in analyzing text files with a binary search tree implementation. To implement the hash table, I used the same MapSet interface that I used to implement my binary search tree in Lab 7. In my Hashmap implementation, I used chaining with a linked list implementation to handle collisions so that multiple entries can be stored at one location. I had two constructors for the Hashmap class, a default one and one that takes the capacity as a parameter. I also had a Hashnode subclass, each node holding the key-value pairs, put, find, and remove methods, and getter & setter methods for the keys and values of the hash nodes. After I was done implementing the hash table, I created a WordCounter2 class to analyze the text files and examine efficiency. The constructor of this class took the data structure type (hashmap of binary search tree) as a parameter and created a BST map or a hash map accordingly. Just like the WordCounter class in Lab 7, I had methods to read and write word count files given the filenames in this class, as well. Similarly, I also had methods returning the total word count, unique word count, word frequencies and the number of times a specific word occurred in the txt file. I also added a getDepth method to my BST implementation that enabled me to learn the depth of the BST, which I didn’t have in the last project. I used that data in my analysis in the Results section below. The main function of the WordCounter2 class calculates the robust average of the time it takes to build the hashmap and the BSTmap after reading the words into an ArrayList. To calculate the robust average, I used a for loop and built the map 4 times, stored the run times, then calculated the average after dropping the lowest and the highest run time value. The main method takes the filenames of the text files from the command line. I ran the program twice for each reddit comment file, once using a BST map and once using a hash map, and analyzed the statistics associated, which are demonstrated in the Results section.
## Results
Table 1 below shows the runtime, total word count, unique word count, and the depth of the BST for the reddit comment files between years 2008-2015 when analyzed using a BST implementation.

<p align="center">

<img width="354" alt="Screen Shot 2022-11-08 at 23 25 13" src="https://user-images.githubusercontent.com/113384943/200738392-cef08b37-e9e9-47d8-b556-22867abe40d3.png">

</p>
<em>Table 1) Data calculated for the Reddit comment files 2008-2005 using a Binary Search Tree Map</em>


Table 2 below shows the runtime, and the number of collisions for the reddit comment files between years 2008-2015 when analyzed using a hash map implementation. I did not include the total word count and the unique word count in this table since it is the same, although I checked that the implementation was working correctly and I got the same results printed on the terminal for the word counts.

<p align="center">
<img width="364" alt="Screen Shot 2022-11-08 at 23 28 13" src="https://user-images.githubusercontent.com/113384943/200738751-2500777f-8967-4c62-82ae-9c5ed6248806.png">
</p>

<em>Table 2) Data calculated for the Reddit comment files 2008-2005 using a Hash Map</em>

 Below are the graphs demonstrating and comparing these results. Fig. 1 shows both the BST map and Hash map runtimes for the files. Years of the comment files are shown on the x axis and the time in ms is shown on the y axis. We can see that the hash map is clearly faster than the BST Map on this data set, the reason is that the time it takes to the binary search tree is higher than the time it takes to search the hash table.
Runtime (ms)
<p align="center">
<img width="561" alt="Screen Shot 2022-11-08 at 23 29 07" src="https://user-images.githubusercontent.com/113384943/200738869-515fa1dd-51fa-41b9-b073-592bab22d487.png">
</p>

We can see that both methods follow a similar increasing pattern as the years go by, with the most dramatic increase happening between 2008-2010. We can see on Table 1 that the total and unique word counts significantly increased during that time period so these results make sense. Fig. 2 below demonstrates this relationship between the total number of words and the runtimes for both the BST map and the hash map implementation.

<p  align="center">
<img width="568" alt="Screen Shot 2022-11-08 at 23 29 49" src="https://user-images.githubusercontent.com/113384943/200738927-c03707ed-972b-4a7c-812c-9bed301eec64.png">
</p>

We can see that the runtimes for the hash map are significantly lower than the runtimes for the BST map at any word count. Although not a linear relationship, we can also observe that as the word counts increase, the runtimes increase with some exceptions. Although I wouldn’t call this a direct relationship. Fig. 3 below demonstrates the graph of the unique number of words and the runtimes for both the BST map and the hash map implementation.

<p align="center">
<img width="570" alt="Screen Shot 2022-11-08 at 23 30 34" src="https://user-images.githubusercontent.com/113384943/200738997-cc87972a-a539-47ca-87c2-38c7194f01ed.png">
</p>


In this graph, we can more clearly see the effect of the increase in the number of unique words on runtime in both hashmap and BST map implementation analysis. Generally, as the unique word count increases, the runtime increases and this can be seen in the upward trends on this graph.
## Analysis of the BSTMap vs Hashtable performance:
Using a Hash table data structure definitely yields more efficient results and is faster on this data set. Ideal performance that we are trying to get as close as possible is the run time O(c), which is a constant runtime. In a good BST which is generally full and balanced, we get the runtime O(log(n)) but in hash tables since for every key, the hash functions reference a single location on the hash table, we can get constant runtimes. However, the hash table we used was not the ideal hash table with every key-value pair occupying a separate location and collisions happened. I used linkedlist chaining to handle these collisions, which means some key-value pairs were located at the same location on the table, increasing the runtime of the search. Even though that was the case, the performance of the hash table data structure for this data set was significantly better than the binary search tree structure. Looking at Table 1- Table 2 and analyzing the relationship between the runtimes and the depth of BST / number of collisions, we can say that as the depth of the BST increases the runtime increases. As for the
Hash table data structure, we can see that as the number of collisions increases the runtime increases.


### References & Acknowledgements
- Prof. Allen Harper, Colby College Computer Science Department 
- Prof. Max Bender, Colby College Computer Science Department 
- [Chandrachud Gowda](https://github.com/Chandrachud-2003)

