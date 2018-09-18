import java.net.*;

public class MulticastSender implements Runnable{
    MulticastSender( int port, String group_ip ){
        GROUP_ADDR = group_ip;
        PORT = port;
    }
    @Override
    public void run() {
        try (MulticastSocket s = new MulticastSocket(PORT)){
            InetAddress group = InetAddress.getByName(GROUP_ADDR);
            s.joinGroup(group);
            DatagramSocket socket = new DatagramSocket();
            String msg = "hello!";
            while( true ){
                DatagramPacket helloPacket = new DatagramPacket(msg.getBytes(), msg.length(), group, PORT);
                socket.send(helloPacket);
                Thread.sleep(TIME_OF_SLEEP);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private final String GROUP_ADDR;
    private final int PORT;
    private final int TIME_OF_SLEEP = 3000;
}
