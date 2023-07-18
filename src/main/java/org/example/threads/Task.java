package org.example.threads;

class Task implements Runnable {
    private final String number;

    public Task(String number) {
        this.number = number;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Number " + number);
        process();
        System.out.println(Thread.currentThread().getName() + " End. Number " + number);
    }

    private void process() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
