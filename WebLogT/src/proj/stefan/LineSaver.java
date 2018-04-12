package proj.stefan;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class LineSaver implements Runnable {
    private MessageQueue out_queue;
    private String out_file;
    public boolean stop = false;

    /*************************************************/

    public LineSaver(MessageQueue out_queue, String out_file) {
        this.out_queue = out_queue;
        this.out_file = out_file;
    }

    public void run() {
        try (BufferedWriter writer =
            new BufferedWriter(
                new FileWriter(this.out_file)
            )
        ) {
            while (!stop) {
                String message = this.out_queue.pull();
                writer.write(message);
                writer.newLine();
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop_saver() {
        this.stop = true;
    }
}
