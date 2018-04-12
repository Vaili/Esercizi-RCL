package proj.stefan;

import java.io.*;

public class LineFetcher implements Runnable {
    private String inputFile;
    private MessageQueue queue;

    /*-------------------------------------------------*/

    public LineFetcher(String inFile, MessageQueue queue) {
        this.inputFile = inFile;
        this.queue = queue;
    }

    @Override
    public void run() {
        try (BufferedReader reader  =
            new BufferedReader(
                new FileReader(this.inputFile)
            )
        ) {
            String message;
            while ((message = reader.readLine()) != null) {
                this.queue.push(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
