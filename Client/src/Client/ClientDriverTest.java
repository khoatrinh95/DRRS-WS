package Client;

import server.DRRSServer;
import server.DRRSServerService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class ClientDriverTest {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://127.0.0.1:8100/auth?wsdl");

            DRRSServerService service = new server.DRRSServerService(url);
            DRRSServer hello = service.getDRRSServerPort();
            System.out.println(hello.bookRoom("DVLS1234","westmount","001", "12/06/2021", 3));
//            System.out.println(hello.createRoom("001", "12/01/2021", "07:00", "19:00"));
//
//            url = new URL("http://127.0.0.1:8200/auth?wsdl");
//            DRRSServerService service2 = new server.DRRSServerService(url);
//            DRRSServer hello2 = service2.getDRRSServerPort();
//            System.out.println(hello2.createRoom("001", "12/01/2021", "07:00", "19:00"));
        } catch (MalformedURLException urlException) {
//            logs.severe("There has been a problem with URL.\nMessage: " + urlException.getMessage());
            return;
        }
    }
}
