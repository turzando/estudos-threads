package org.example.threads;

import lombok.SneakyThrows;

import java.time.Duration;
import java.util.Date;

import static org.example.threads.VirtualThreadExample.alwaysTrue;

public class Work {

  static void workForever() {
    System.out.println("I'm working hard " + Thread.currentThread());
    try {
      while (alwaysTrue()) {
        sleep(Duration.ofMillis(100));
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    System.out.println("I'm done with working hard " + Thread.currentThread());
  }

  static void easyWork() {
    try {
      System.out.println("I'm working easy | " + Thread.currentThread());
      sleep(Duration.ofSeconds(1));
      System.out.println("I'm done with working easy | " + Thread.currentThread());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

  }

  @SneakyThrows
  private static void sleep(Duration duration) throws InterruptedException {
    Thread.sleep(duration);
  }

  private static String getDate() {
    Date date = new Date();
    return date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
  }
}
