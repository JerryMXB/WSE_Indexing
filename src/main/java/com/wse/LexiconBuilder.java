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

    /**
     * build the lexicon and inverted index
     */
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
            int length = 0;
            int count = 0;

            while ((buffer = bufferedReader.readLine()) != null) {
                String[] params = buffer.split(" ");
                if (!word.equals(params[0])) {
                    if (pointer == 0) {
                        System.out.println("Generating word:" + word + " lexicon, No." + IndexerConstant.WORD_ID);
                        lexiconOut.print(IndexerConstant.WORD_ID + " " +  pointer + " ");
                    }
                    else {
                        System.out.println("Generating wordId:" + word + " lexicon, No." + IndexerConstant.WORD_ID);
                        lexiconOut.print(length + " " + count + "\n" + IndexerConstant.WORD_ID + " " +  ++pointer + " ");
                        count = 0;
                        length = 0;
                    }
                    byte[] compressedInt = VbyteCompress.encode(Integer.valueOf(params[1]));
                    length += compressedInt.length;
                    invertedIndexOut.write(compressedInt);

                    byte[] compressedInt2 = VbyteCompress.encode(Integer.valueOf(params[2]));
                    length += compressedInt2.length;
                    invertedIndexOut.write(compressedInt2);

                    pointer += compressedInt.length + compressedInt2.length;
                    word = params[0];
                    wordIdTable.put(word, IndexerConstant.WORD_ID++);
                    count++;
                }
                else {
                    byte[] compressedInt = VbyteCompress.encode(Integer.valueOf(params[1]));
                    length += compressedInt.length;
                    invertedIndexOut.write(compressedInt);

                    byte[] compressedInt2 = VbyteCompress.encode(Integer.valueOf(params[2]));
                    length += compressedInt2.length;
                    invertedIndexOut.write(compressedInt2);

                    pointer += compressedInt.length + compressedInt2.length;
                    count++;
                }
            }
            lexiconOut.print(length + " " + count + "\n");
            lexiconOut.close();
            invertedIndexOut.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Write the word table to file
     */
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
