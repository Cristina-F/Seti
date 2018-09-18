import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MulticastReceiver implements Runnable {
    MulticastReceiver( int port, String group_ip ){
        GROUP_ADDR = group_ip;
        PORT = port;
    }
    @Override
    public void run() {
        try( MulticastSocket s = new MulticastSocket(PORT)) {
            InetAddress group = InetAddress.getByName(GROUP_ADDR);
            s.joinGroup(group);
            while (true ) {
                byte[] buf = new byte[SIZE_BUF];
                DatagramPacket receivedPacket = new DatagramPacket(buf, buf.length);
                s.receive(receivedPacket);
                String msg1 = new String(buf, 0, buf.length);
                //System.out.println(recv + "  " +  msg1 + "  " + recv.getSocketAddress() + "  " + recv.getPort());
                tableOfActivePorts.put(receivedPacket.getPort(), System.currentTimeMillis() / 1000);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void printActivePorts(){
        Long time = System.currentTimeMillis() / 1000;
        ArrayList<Integer> keys = new ArrayList<>();
        for (Map.Entry entry : tableOfActivePorts.entrySet()) {
            Long value = (Long)entry.getValue();
            if (time - value > TIMEOUT ) {
                keys.add((Integer)entry.getKey());
            }
            else {
                System.out.println("port: " + entry.getKey() + "   time:" + entry.getValue());
            }
        }
        deleteInactiveKeys(keys);
    }
    private void deleteInactiveKeys(ArrayList<Integer> keys) {
        for( Integer i: keys ){
            tableOfActivePorts.remove(i);
        }
    }
    private final String GROUP_ADDR;
    private final int PORT;
    private final int SIZE_BUF = 7;
    private final int TIMEOUT = 6;
    private HashMap<Integer, Long> tableOfActivePorts = new HashMap<>();
}
