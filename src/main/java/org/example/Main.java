package org.example;

import org.example.threads.VirtualThreadExample;

public class Main {
    public static void main(String[] args) {
//        ExecutorServiceExamples.execute();
//        ExecutorServiceExamples.submitRunnableWithoutTask();
//        ExecutorServiceExamples.submitRunnableWithTask();
//        ExecutorServiceExamples.submitCallable();
//        ExecutorServiceExamples.invokeAny();
//        ExecutorServiceExamples.invokeAll();

//        ExecutorServiceExamples.shutdownNow();
//        ExecutorServiceExamples.awaitTermination();

        VirtualThreadExample.run();
//        VirtualThreadExample.runWithCooperativeScheduling();
//        VirtualThreadExample.runWithPinnedVirtualThreads();

    }

}

