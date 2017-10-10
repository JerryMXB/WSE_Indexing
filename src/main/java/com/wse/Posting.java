package com.wse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaoqunhuang on 10/10/17.
 */
public class Posting {
    public Map<String, Integer> getWordsList(String content) {
        String[] words = content.split("\\W+");
        Map<String, Integer> wordList = new HashMap<String, Integer>();
        for (String s : words) {
            if (wordList.containsKey(s)) {
                int count = wordList.get(s);
                wordList.put(s, ++count);
            }
            else {
                wordList.put(s, 1);
            }
        }
        return wordList;
    }
}
