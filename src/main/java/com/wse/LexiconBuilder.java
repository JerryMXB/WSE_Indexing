package com.wse;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by chaoqunhuang on 10/12/17.
 */
public class LexiconBuilder {
    private static Map<String, Integer> wordIdTable= new HashMap<>();

    public static void build() {
        try {
            FileInputStream fileInputStream = new FileInputStream(FilePath.INTERMEDIATE_POSTING_SORTED);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            FileOutputStream invertedIndexOut = new FileOutputStream(FilePath.INVERTED_INDEX);
            PrintWriter lexiconOut = new PrintWriter(new FileWriter(FilePath.LEXICON,true));
            String buffer;
            int pointer = -1;
            String word = "";
            while ((buffer = bufferedReader.readLine()) != null) {
                String[] params = buffer.split(" ");
                if (!word.equals(params[0])) {
                    lexiconOut.println(IndexerConstant.WORD_ID + " " +  ++pointer);
                    String invertedIndex =  params[1] + ":" + params[2] + " ";
                    byte[] binaryIndex = invertedIndex.getBytes();
                    invertedIndexOut.write(binaryIndex);
                    pointer += binaryIndex.length;
                    word = params[0];
                    wordIdTable.put(word, IndexerConstant.WORD_ID++);
                }
                else {
                    String invertedIndex =  params[1] + ":" + params[2] + " ";
                    byte[] binaryIndex = invertedIndex.getBytes();
                    invertedIndexOut.write(binaryIndex);
                    pointer += binaryIndex.length;
                }
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
