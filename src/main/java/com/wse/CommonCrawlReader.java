package com.wse;

import com.google.common.base.CharMatcher;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * Created by chaoqunhuang on 10/10/17.
 */
public class CommonCrawlReader {
    private Map<String, Integer> docIdTable= new HashMap<String, Integer>();
    private Posting posting = new Posting();

    public void startParser(String fileName) {
        try{
            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            // Skip the metadata for the file
            skipLines(bufferedReader, 18);

            // Start reading page
            int count = 10;
            String buffer;
            while((buffer = bufferedReader.readLine()) != null) {
                StringBuilder sb = new StringBuilder();
                String url = "";
                while (!"".equals(buffer)) {
                    buffer = bufferedReader.readLine();
                    // Print out metadata
                    if (buffer.startsWith("WARC-Target-URI: ")) {
                        url = buffer.split("WARC-Target-URI: ")[1];
                    }
                    System.out.println(buffer);
                }
                buffer = bufferedReader.readLine();
                while (!"WARC/1.0".equals(buffer) && buffer != null) {
                    buffer = bufferedReader.readLine();
                    sb.append(" " + buffer);
                }
                if (CharMatcher.ASCII.matchesAllOf(sb.toString())) {
                    System.out.println(sb);
                    System.out.println(url);
                    docIdTable.put(url, IndexerConstant.DOC_ID);
                    posting.postToIntermediateFile(sb.toString(), IndexerConstant.DOC_ID++);
                    //if (count-- < 0) break;
                }
                else {
                    System.out.println("Not English");
                }
            }
            System.out.println("Intermediate posting is done");
            System.out.println("=====================================================================");
            System.out.println("Calling unix sort");
            SortUtil.sortUsingUnixSort(FilePath.INTERMEDIATE_POSTING, FilePath.INTERMEDIATE_POSTING_SORTED);
            outputUrlTable();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void skipLines(BufferedReader bufferedReader, int lines) {
        for (int i = 0; i < lines; i++) {
            try {
                bufferedReader.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void outputUrlTable() {
        try {
            System.out.println("Generating Url Table");
            File file = new File(FilePath.URL_TABLE);
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(new FileWriter(file,true));
            docIdTable.forEach((k, v) -> {
                if (!"".equals(k)) {
                    printWriter.println(k + " " + v);
                }
            });
            printWriter.close();
            SortUtil.sortUsingUnixSort(FilePath.URL_TABLE, FilePath.URL_TABLE_SORTED);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
