package basic.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IP {

    public static void main(String[] args) {
        try {
            InetAddress ip = InetAddress.getLocalHost();

            String ipStr = ip.getHostAddress();

            System.out.println("ip=" + ipStr);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
