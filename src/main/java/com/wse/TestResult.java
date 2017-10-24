package com.wse;

import java.io.*;

/**
 *
 * Created by chaoqunhuang on 10/13/17.
 */
public class TestResult {
    public static void testInvertedIndex() {
        try {
            FileInputStream fileInputStream = new FileInputStream(FilePath.INVERTED_INDEX);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);

            FileInputStream fileInputStream2 = new FileInputStream(FilePath.LEXICON);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream2));
            String[] params = bufferedReader.readLine().split(" ");
            byte[] res = new byte[Integer.parseInt(params[2])];
            dataInputStream.read(res, Integer.parseInt(params[1]), Integer.parseInt(params[2]));
            System.out.println(params[2]);
            int[] result = VbyteCompress.decode(res, Integer.parseInt(params[3]) * 2);
            System.out.println(result.length);
            for (int i : result) {
                System.out.print(i + " ");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
