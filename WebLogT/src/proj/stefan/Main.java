package proj.stefan;

public class Main {

    public static void main(String[] args) {
        MessageQueue queues[] = {
                new MessageQueue(25),
                new MessageQueue(25)
        };

        Thread fetcher = new Thread(new LineFetcher("C:\\Users\\Stefan\\IdeaProjects\\Esercizi RCL\\WebLogT\\src\\proj\\stefan\\web.log.txt", queues[0]));
        fetcher.start();

        Thread saver = new Thread(new LineSaver(queues[1], "C:\\Users\\Stefan\\IdeaProjects\\Esercizi RCL\\WebLogT\\src\\proj\\stefan\\web.log.out.txt"));
        saver.start();

        Thread parsers[] = new Thread[10];

        for (int i = 0; i < 10; i++) {
            parsers[i] = new Thread(new LineParser(queues[0], queues[1]));
            parsers[i].start();
        }

        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fetcher.interrupt();
        saver.interrupt();
        for (int i = 0; i < 10; i++) {
            parsers[i].interrupt();
        }
    }
}
