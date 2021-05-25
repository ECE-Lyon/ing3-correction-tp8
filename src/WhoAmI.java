import java.net.InetAddress;
import java.net.UnknownHostException;

public class WhoAmI {

    public static void main(String[] args) throws UnknownHostException {
        whoAmI("www.google.com");
    }

    private static void whoAmI(String name) throws UnknownHostException {
        System.out.println(InetAddress.getByName(name).getHostAddress());
    }
}
