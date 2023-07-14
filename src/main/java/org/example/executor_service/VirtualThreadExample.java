package org.example.executor_service;

import lombok.SneakyThrows;

import java.time.Duration;
import java.util.Date;

public class VirtualThreadExample {


  static ThreadLocal<String> context = new ThreadLocal<>();

  public static void runWithCooperativeScheduling() {
    try {
      var artur = workingForever();
      var tony = takeABreak();

      artur.join();
      tony.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void runWithoutCooperativeScheduling() {
    try {
      var artur = easyWork();
      var tony = takeABreak();

      artur.join();
      tony.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void runWithPinnedVirtualThreads() {
    try {
      var artur = goToTheBathroom();
      var tony = takeABreak();

      artur.join();
      tony.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
  static Thread takeABreak() {
    return virtualThread(
        "Take a break",
        () -> {
          System.out.println("I'm going to take a break | " + new Date() + " " + Thread.currentThread());
          try {
            sleep(Duration.ofSeconds(1));
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
          System.out.println("I'm done with the break | " + new Date() + " " + Thread.currentThread());
        });
  }

  static void virtualThreadLocal() throws InterruptedException {
    var virtualThread1 = Thread.ofVirtual().name("thread-1").start(() -> {
      context.set("thread-1");
      try {
        sleep(Duration.ofSeconds(1L));
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println("Hey, my name is " + context.get());
    });

    System.out.println("fora de uma thread -> " + context.get());

    var virtualThread2 = Thread.ofVirtual().name("thread-2").start(() -> {
      context.set("thread-2");
      try {
        sleep(Duration.ofSeconds(1L));
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println("Hey, my name is " + context.get());
    });
    virtualThread1.join();
    virtualThread2.join();
  }

  static Thread workingForever() {
    return virtualThread(
        "Working consciousness", Work::workForever
    );
  }

  static Thread easyWork() {
    return virtualThread(
        "Working consciousness", Work::easyWork
    );
  }

  static Thread goToTheBathroom() {
    return virtualThread(
        "Go to the toilet", Bathroom::useTheToilet
    );
  }

  static boolean alwaysTrue() {
    return true;
  }


  private static Thread virtualThread(String name, Runnable runnable) {
    return Thread.ofVirtual()
        .name(name)
        .start(runnable);
  }

  @SneakyThrows
  private static void sleep(Duration duration) throws InterruptedException {
    Thread.sleep(duration);
  }
}

