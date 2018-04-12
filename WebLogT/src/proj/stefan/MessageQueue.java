package proj.stefan;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {
    private final String[] queue;
    public Condition notFull;
    public Condition notEmpty;
    private int pullIndex;
    private int pushIndex;
    private int counter;
    private int cardinal_id;
    private Lock queueLock;

    /*****************************************/

    public MessageQueue(int size) {
        this.queueLock = new ReentrantLock();
        this.queue = new String[size];
        this.notFull = queueLock.newCondition();
        this.notEmpty = queueLock.newCondition();
        this.pullIndex = 0;
        this.pushIndex = 0;
    }

    // EFFECTS: Inserisce nelle coda circolare. Se la coda e' piena si mette in attesa.
    public void push(String message) {
        this.queueLock.lock();

        try {
            try {
                while (this.queue.length == this.counter) {
                    this.notFull.await();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.queue[this.pushIndex] = message;
            this.pushIndex = (this.pushIndex + 1) % this.queue.length;
            this.counter++;
            this.notEmpty.signal();
        } finally {
            this.queueLock.unlock();
        }
    }

    // EFFECTS: Preleva dalla coda circolare. Se la coda e' vuota si mette in attesa.
    public String pull() {
        this.queueLock.lock();
        try {
            try {
                while (this.counter == 0) {
                    this.notEmpty.await();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String toPass = this.queue[pullIndex];
            this.pullIndex = (this.pullIndex + 1) % queue.length;
            this.counter--;
            this.notFull.signal();
            return toPass;
        } finally {
            this.queueLock.unlock();
        }
    }
}
