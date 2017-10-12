package com.wse;

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
    private Map<String, Integer> wordIdTable= new HashMap<String, Integer>();

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

    public void postToIntermediateFile(String content, int docId) {
        Map<String, Integer> wordList = getWordsList(content);
        System.out.println(wordList.toString());
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter("src/../IntermediatePosting/posting.txt",true));
            wordList.forEach((k, v) -> {
                printWriter.println(k + " " + docId + " " + v);
            });
            printWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sortUsingUnixSort() {
        try {
            String cmd[] = {
                "/bin/sh",
                "-c",
                "sort /Users/chaoqunhuang/projects/wse/hw2/IntermediatePosting/posting.txt > /Users/chaoqunhuang/projects/wse/hw2/IntermediatePosting/posting_sorted.txt"
            };
            Process p = Runtime.getRuntime().exec(cmd);
            int exit = p.waitFor();
            System.out.println(exit);
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
