package proj.stefan;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LineParser implements Runnable {
    private MessageQueue in_queue;
    private MessageQueue out_queue;

    /*******************************************/

    public LineParser(MessageQueue in_queue, MessageQueue out_queue) {
        this.in_queue = in_queue;
        this.out_queue = out_queue;
    }

    @Override
    public void run() {
        String message = this.in_queue.pull();

        String tokens[] = message.split(" - - ");
        InetAddress address = null;

        try {
            address = InetAddress.getByAddress(getBytesIP(tokens[0]));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String hostName = address.getCanonicalHostName();
        hostName = hostName + " - - " + tokens[1];

        this.out_queue.push(hostName);
    }

    private byte[] getBytesIP(String token) {
        String ip_tokens[] = token.split("\\.");
        byte ip_address[] = new byte[4];

        for (int i = 0; i < ip_address.length; i++) {
            ip_address[i] = (byte) Integer.parseInt(ip_tokens[i]);
        }

        return ip_address;
    }
}
