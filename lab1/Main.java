import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {

        if(args.length < 2 ) {
            System.err.println("WHERE arguments???");
            return;
        }
        Parser parser = new Parser(args);
        if (!parser.parse()){
            System.err.println("Stupid arguments!1!!!1");
            return;
        }
        Thread threadSender = new Thread(new MulticastSender(parser.getPort(), parser.getIp()));
        threadSender.start();
        MulticastReceiver multicastReceiver = new MulticastReceiver(parser.getPort(), parser.getIp());
        Thread threadReceiver = new Thread(multicastReceiver);
        threadReceiver.start();
        while (true) {
            multicastReceiver.printActivePorts();
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("--------");
        }
    }
}
