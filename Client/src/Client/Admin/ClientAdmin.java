package Client.Admin;

import Client.Admin.AdminOperations;
import authentication.AuthenticationOperations;
import authentication.AuthenticationOperationsService;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExtHelper;
import server.DRRSServer;
import server.DRRSServerService;

import java.net.MalformedURLException;
import java.net.URL;

public class ClientAdmin {
    static AdminOperations adminOperations;
    public static void main(String[] args) {

        // authenticate admin
        int campusPort;
        do {
            try {
                URL url = new URL("http://127.0.0.1:9000/auth?wsdl");

                AuthenticationOperationsService service = new authentication.AuthenticationOperationsService(url);
                AuthenticationOperations authentication = service.getAuthenticationOperationsPort();
                adminOperations = new AdminOperations(authentication);
                campusPort = adminOperations.authenticateAdmin();

                if (campusPort==-1){
                    System.out.println("Cannot authenticate admin");
                }
            } catch (MalformedURLException urlException) {
//            logs.severe("There has been a problem with URL.\nMessage: " + urlException.getMessage());
                return;
            }
        } while (campusPort==-1);

        String adminId = adminOperations.getAdminId();
        // connect to admin server
        try {
            URL url = new URL("http://127.0.0.1:"+ campusPort +"/auth?wsdl");

            DRRSServerService service = new server.DRRSServerService(url);
            DRRSServer DRRSserver = service.getDRRSServerPort();
            adminOperations = new AdminOperations(DRRSserver, adminId);
            adminOperations.askActionFromAdmin(campusPort);

        } catch (MalformedURLException urlException) {
//            logs.severe("There has been a problem with URL.\nMessage: " + urlException.getMessage());
            return;
        }

//        String adminId = adminOperations.getAdminId();
        // connect user to their server
//        try {
//            drrs = DRRSHelper.narrow(ncRef.resolve_str(campusName));
//            adminOperations = new AdminOperations(drrs, adminId);
//            adminOperations.askActionFromAdmin(campusName);
//        } catch (Exception e) {
//            System.out.println("Cannot connect user to server");
//        }

    }
}
