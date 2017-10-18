package com.wse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        // Parsing Intermediate Posting
        ExecutorService es = Executors.newFixedThreadPool(20);
        CommonCrawlReader commonCrawlReader = new CommonCrawlReader();
        CompletableFuture<Void> runAsync = new CompletableFuture<>();
        for (String wet : FilePath.WETS) {
            runAsync = CompletableFuture.runAsync(() -> commonCrawlReader.startParser(wet), es);
        }
        while (true) {
            if (runAsync.isDone()) break;
        }
        // Calling Unix Sort
        System.out.println("Intermediate posting is done");
        System.out.println("=====================================================================");
        System.out.println("Calling unix sort");
        SortUtil.sortUsingUnixSortWith(FilePath.INTERMEDIATE_POSTING, FilePath.INTERMEDIATE_POSTING_SORTED);
        commonCrawlReader.outputUrlTable();

        // Building index
        LexiconBuilder.build();
        LexiconBuilder.outputWordList();
        //TestResult.testInvertedIndex();
        System.out.println(IndexerConstant.PAGE_NO);
    }
}
