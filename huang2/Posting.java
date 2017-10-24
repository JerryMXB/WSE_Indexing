package com.wse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by chaoqunhuang on 10/10/17.
 */
public class Posting {

    /**
     * Get the words in a String
     * @param content a String
     * @return a word list
     */
    public Map<String, Integer> getWordsList(String content) {
        String[] words = content.split("\\W+");
        Map<String, Integer> wordList = new HashMap<>();
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

    /**
     * Parser postings and write to fil
     * @param content a String
     * @param docId The doc Id
     */
    public void postToIntermediateFile(String content, int docId) {
        Map<String, Integer> wordList = getWordsList(content);
        System.out.println(wordList.toString());
        synchronized (Posting.class) {
            try {
                File file = new File(FilePath.INTERMEDIATE_POSTING);
                file.createNewFile();
                PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));

                wordList.forEach((k, v) -> {
                    if (!"".equals(k)) {
                        printWriter.println(k + " " + docId + " " + v);
                    }
                });
                printWriter.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
