public class Parser {
    Parser(String[] args){
        port = Integer.valueOf(args[0]);
        ip = args[1];
        System.out.println(port + "  " + ip);
    }
    public boolean parse(){
        if ( port < 1024 || port > 49151 ){
            return false;
        }
        String[] piecesIp = ip.split(".");
        int count = 1;
        for ( String i: piecesIp ) {
            int piece = Integer.parseInt(i);
            if ( count == 0 && (piece < 224 || piece > 239 )) {
                return false;
            }
            else if (count > 4 || piece < 0 || piece > 255 ) {
                return false;
            }
            count++;
        }
        return true;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    private int port;
    private String ip;
}
