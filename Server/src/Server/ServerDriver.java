package Server;

import Authentication.AuthenticationOperations;
import UDP.UdpOperations;
import org.omg.CORBA.ORB;

import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerDriver {
    private static ORB orb;
    public static void main(String args[]) {
        // Starting Authentication server
        AuthenticationOperations authenticationOperations = new AuthenticationOperations();
        Endpoint ep = Endpoint.create(authenticationOperations);
        ep.publish("http://127.0.0.1:9000/auth");


        // Starting DRRS servers
        DRRSServer server1 = new DRRSServer("Westmount");
        DRRSServer server2 = new DRRSServer("Kirkland");
        DRRSServer server3 = new DRRSServer("Dorval");

        ep = Endpoint.create(server1);
        ep.publish("http://127.0.0.1:8100/auth");

        ep = Endpoint.create(server2);
        ep.publish("http://127.0.0.1:8200/auth");

        ep = Endpoint.create(server3);
        ep.publish("http://127.0.0.1:8300/auth");

//        DRRSAndAuthenticationThread drrsAndAuthenticationThread = new DRRSAndAuthenticationThread(args);
//        Thread serverThread = new Thread(drrsAndAuthenticationThread);
//        serverThread.start();

        // Starting UDP server
        try{
            DatagramSocket ds = new DatagramSocket(8000);
            byte[] receive = new byte[65535];
            DatagramPacket dpReceive = null;
            System.out.println("The UDP server started on port 8000");
            System.out.println("UDP server running...");
            while(true) {
                dpReceive = new DatagramPacket(receive, receive.length);
                try {
                    ds.receive(dpReceive);
                    UdpOperations udpOperations = new UdpOperations(ds, dpReceive);
                    udpOperations.start();
                } catch (IOException e) {
                    System.out.println("Error receiving packet.\nMessage: " + e.getMessage());
                }
            }
        } catch (SocketException e){
            System.out.println("Error starting UDP server. \nMessage: " + e.getMessage());
        }

        System.out.println("UDP Exiting ...");

    }

}
