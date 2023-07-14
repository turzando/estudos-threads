package org.example;

import org.example.executor_service.VirtualThreadExample;

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
//        VirtualThreadExample.runWithCooperativeScheduling();
//        VirtualThreadExample.runWithoutCooperativeScheduling();
        VirtualThreadExample.runWithPinnedVirtualThreads();
    }
}

