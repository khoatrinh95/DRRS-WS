package Server;

import Authentication.AuthenticationOperations;

import java.util.ArrayList;
import java.util.List;

public class DRRSAndAuthenticationThread implements Runnable{
    private String[] args;

    public DRRSAndAuthenticationThread(String[] args) {
        this.args = args;
    }

    @Override
    public void run() {
        try {
            // create servant and register it with the ORB
            DRRSServer server1 = new DRRSServer("Westmount");
            DRRSServer server2 = new DRRSServer("Dorval");
            DRRSServer server3 = new DRRSServer("Kirkland");

            List<DRRSServer> serverList = new ArrayList<>();
            serverList.add(server1);
            serverList.add(server2);
            serverList.add(server3);

            // create authentication server obj
            AuthenticationOperations authenticationOperations = new AuthenticationOperations();

            // register the 3 servers and authentication server
            registerServer(serverList, authenticationOperations, args);

        } catch (Exception e) {
            System.err.println("ERROR: " + e);

        }
    }

    private void registerServer(List<DRRSServer> serverList, AuthenticationOperations authenticationOperations, String[] args) {
        try {
            // create and initialize the ORB
            //
            // get reference to rootpoa &amp; activate the POAManager
//            orb = ORB.init(args, null);
//            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
//            rootpoa.the_POAManager().activate();
//
//            org.omg.CORBA.Object objRef =  orb.resolve_initial_references("NameService");
//            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
//
//            if (serverList != null) {
//                for (DRRSServer server : serverList) {
//                    // create servant and register it with the ORB
//                    server.setORB(orb);
//
//                    // get object reference from the servant
//                    org.omg.CORBA.Object ref = rootpoa.servant_to_reference(server);
//                    DRRS href = DRRSHelper.narrow(ref);
//
//                    // register the server with the server name
//                    // Doval, Westmount, Kirkland
//                    NameComponent path[] = ncRef.to_name(server.getCampusName());
//                    ncRef.rebind(path, href);
//
//                    System.out.println(server.getCampusName() + " Server ready and waiting ...");
//                }
//            }
//
//            if (authenticationOperations != null) {
//                authenticationOperations.setORB(orb);
//
//                // get object reference from the servant
//                org.omg.CORBA.Object ref = rootpoa.servant_to_reference(authenticationOperations);
//                Authentication href = AuthenticationHelper.narrow(ref);
//
//                NameComponent path[] = ncRef.to_name("Authentication");
//                ncRef.rebind(path, href);
//
//                System.out.println("Authentication Server ready and waiting ...");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
