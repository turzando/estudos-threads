package org.example.executor_service;

import lombok.SneakyThrows;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.example.executor_service.VirtualThreadExample.alwaysTrue;

public class Work {

  static void workForever() {
    System.out.println("I'm working hard | " + new Date() + " " + Thread.currentThread());
    try {
      while (alwaysTrue()) {
        sleep(Duration.ofMillis(100));
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    System.out.println("I'm done with working hard | " + new Date() + " " + Thread.currentThread());
  }

  static void easyWork() {
    try {
      System.out.println("I'm working hard | " + new Date() + " " + Thread.currentThread());
      sleep(Duration.ofSeconds(2));
      System.out.println("I'm done with working hard | " + new Date() + " " + Thread.currentThread());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

  }

  @SneakyThrows
  private static void sleep(Duration duration) throws InterruptedException {
    Thread.sleep(duration);
  }
}
