package org.example.threads;

import java.time.Duration;
import java.util.Date;

import static java.lang.Thread.sleep;

public class Bathroom {
  synchronized static void useTheToilet() {
    try {
      System.out.println("I'm going to use the toilet | " + Thread.currentThread());
      sleep(Duration.ofSeconds(3));
      System.out.println("I'm done with the toilet | " + Thread.currentThread());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
