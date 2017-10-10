package com.wse;

public class Main {

    public static void main(String[] args) {
        CommonCrawlReader commonCrawlReader = new CommonCrawlReader();
        commonCrawlReader.startParser(
                "/Users/chaoqunhuang/projects/wse/hw2/CC-MAIN-20170919112242-20170919132242-00000.warc.wet");
    }
}
