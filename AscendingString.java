/**
 * Lab 6
 * File: AscendingString.java
 * Author: Zehra Gundogdu
 * Date: 4/5/2022
 */

import java.util.Comparator;

public class AscendingString implements Comparator<String>{


    public int compare(String arg1, String arg2){
        return arg1.compareTo(arg2);
    }
}
