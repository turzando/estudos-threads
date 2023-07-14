package org.example.executor_service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceExamples {

  public static void execute() {
    ExecutorService executor = Executors.newFixedThreadPool(2);
    for (int i = 0; i < 3; i++) {
      Runnable worker = new Task("" + i);
      executor.execute(worker);
    }

    executor.shutdown();
    while (!executor.isTerminated()) {
    }

    System.out.println("Finished all threads");
  }

  public static void submitRunnableWithoutTask() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    try {
      Future<?> future = executor.submit(new Runnable() {
        public void run() {
          System.out.println("submit runnable");
        }
      });

      System.out.println("result ->" + future.get()); //retorna null se foi executada corretamente

    } catch (ExecutionException | InterruptedException ex) {
      System.out.println(ex.getMessage());
    }

    executor.shutdown();
  }

  public static void submitRunnableWithTask() {
    ExecutorService executor = Executors.newSingleThreadExecutor();

    final String[] result = new String[1];
    try {
      Future<String[]> f = executor.submit(new Runnable() {
        public void run() {

          result[0] = "result";
        }
      }, result);

      String[] runnableResult = f.get();
      System.out.println("future result: " + runnableResult[0]);
    } catch (ExecutionException | InterruptedException ex) {
      System.out.println(ex.getMessage());
    }

    executor.shutdown();
  }

  public static void submitCallable() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    try {
      Future<?> future = executor.submit(new Callable() {
        public String call() {
          return "submit runnable";
        }
      });

      System.out.println("result ->" + future.get());

    } catch (ExecutionException | InterruptedException ex) {
      System.out.println(ex.getMessage());
    }

    executor.shutdown();
  }

  public static void invokeAny() {

    ExecutorService executorService = Executors.newFixedThreadPool(3);

    List<Callable<String>> callables = new ArrayList<>();

    callables.add(new Callable<String>() {
      public String call() throws Exception {
        Thread.sleep(1000);
        return "Task 1";
      }
    });
    callables.add(new Callable<String>() {
      public String call() throws Exception {
        return "Task 2";
      }
    });
    callables.add(new Callable<String>() {
      public String call() throws Exception {
        return "Task 3";
      }
    });

    try {
      String result = executorService.invokeAny(callables);
      System.out.println("result = " + result);

    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }

    executorService.shutdown();
  }

  public static void invokeAll() {
    ExecutorService executorService = Executors.newFixedThreadPool(1);

    List<Callable<String>> callables = new ArrayList<>();

    callables.add(new Callable<String>() {
      public String call() throws Exception {
        System.out.println("running 1");
        Thread.sleep(4000);
        return "Task 1";
      }
    });
    callables.add(new Callable<String>() {
      public String call() throws Exception {
        System.out.println("running 2");
        Thread.sleep(4000);
        return "Task 2";
      }
    });
    callables.add(new Callable<String>() {
      public String call() throws Exception {
        System.out.println("running 3");
        Thread.sleep(4000);
        return "Task 3";
      }
    });

    try {
      List<Future<String>> futures = executorService.invokeAll(callables);
      System.out.println("invokedAll");


      for (Future<String> future : futures) {
        System.out.println("future.get = " + future.get());
      }
    } catch (InterruptedException ex) {
      System.out.println(ex.getMessage());
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }

    executorService.shutdown();
  }

  public static void shutdownNow() {
    ExecutorService executor = Executors.newFixedThreadPool(2);
    for (int i = 0; i < 3; i++) {
      Runnable worker = new Task("" + i);
      executor.execute(worker);
    }

    //Aumentar tempo do sleep
    executor.shutdownNow();

    while (!executor.isTerminated()) {
    }

    System.out.println("Finished all threads");
  }

  public static void awaitTermination() {
    ExecutorService executor = Executors.newFixedThreadPool(2);
    for (int i = 0; i < 3; i++) {
      Runnable worker = new Task("" + i);
      executor.execute(worker);
    }


    try {
      executor.shutdown();

      System.out.println("shutdown rodou");
      executor.awaitTermination(10_000L, TimeUnit.MILLISECONDS);

      System.out.println("Finished all threads");

    } catch (InterruptedException exception) {
      System.out.println(exception.getMessage());
    }
  }
}
