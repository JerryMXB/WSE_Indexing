package com.wse;

import java.io.*;

/**
 * Created by chaoqunhuang on 10/13/17.
 */
public class TestResult {
    public static void testInvertedIndex() {
        try {
            FileInputStream fileInputStream = new FileInputStream(FilePath.INVERTED_INDEX);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            for (int i = 0; i < 1000; i++) {
                System.out.println(dataInputStream.readInt() + ":" + dataInputStream.readInt());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
