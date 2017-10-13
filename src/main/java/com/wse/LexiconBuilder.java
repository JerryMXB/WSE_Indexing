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

            File invertedIndexFile = new File(FilePath.INVERTED_INDEX);
            invertedIndexFile.createNewFile();
            DataOutputStream invertedIndexOut = new DataOutputStream(new FileOutputStream(invertedIndexFile));

            File lexicon = new File(FilePath.LEXICON);
            lexicon.createNewFile();
            PrintWriter lexiconOut = new PrintWriter(new FileWriter(lexicon,true));
            String buffer;
            int pointer = 0;
            String word = "";
            int count = 0;

            while ((buffer = bufferedReader.readLine()) != null) {
                String[] params = buffer.split(" ");
                if (!word.equals(params[0])) {
                    if (pointer == 0) {
                        lexiconOut.print(IndexerConstant.WORD_ID + " " +  ++pointer + " ");
                    }
                    else {
                        lexiconOut.print(count + "\n" + IndexerConstant.WORD_ID + " " +  ++pointer + " ");
                        count = 0;
                    }
                    invertedIndexOut.writeInt(Integer.valueOf(params[1]));
                    invertedIndexOut.writeInt(Integer.valueOf(params[2]));
                    pointer += 4 + 4;
                    word = params[0];
                    wordIdTable.put(word, IndexerConstant.WORD_ID++);
                    count++;
                }
                else {
                    invertedIndexOut.writeInt(Integer.valueOf(params[1]));
                    invertedIndexOut.writeInt(Integer.valueOf(params[2]));
                    pointer += 4 + 4;
                    count++;
                }
            }
            lexiconOut.print(count + "\n");
            lexiconOut.close();
            invertedIndexOut.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void outputWordList() {
        try {
            File file = new File(FilePath.WORD_LIST);
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(new FileWriter(file,true));
            wordIdTable.forEach((k, v) -> {
                if (!"".equals(k)) {
                    printWriter.println(v + " " + k);
                }
            });
            printWriter.close();
            SortUtil.sortUsingUnixSort(FilePath.WORD_LIST, FilePath.WORD_LIST_SORTED);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
