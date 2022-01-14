package Authentication;

import Business.Campus;
import org.omg.CORBA.ORB;

import javax.jws.WebService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@WebService
public class AuthenticationOperations {
    private ORB orb;
    private List<Campus> campuses = new ArrayList<>();
    private Logger log;

    public AuthenticationOperations() {
        super();
        Campus campusWestmount = new Campus("Westmount", 8100, "WST");
        Campus campusKirkland = new Campus("Kirkland", 8200, "KKL");
        Campus campusDorval = new Campus("Dorval", 8300, "DVL");
        campuses.add(campusWestmount);
        campuses.add(campusDorval);
        campuses.add(campusKirkland);

        startLogger();

        log.info("Authentication Server created");
    }


    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public int authorize(String id, boolean isAdmin) {
        if (id.length()!= 8)
            return -1;
        String campus = id.substring(0,3);
        String role = id.substring(3,4);
        String number = id.substring(4);
        if (!(campus.equalsIgnoreCase("wst") || campus.equalsIgnoreCase("kkl") || campus.equalsIgnoreCase("dvl")))
            return -1;
        if (!(isInteger(number)))
            return -1;

        if (isAdmin){
            if (!(role.equalsIgnoreCase("a")))
                return -1;
        } else {
            if (!(role.equalsIgnoreCase("s")))
                return -1;
        }

        for (Campus c : campuses){
            if (c.getCode().equalsIgnoreCase(campus))
                return c.getPort();
        }
        return -1;
    }

    private void startLogger() {
        try {
            log =  Logger.getLogger("Authentication Server");
            log.setUseParentHandlers(false);
            String loggerFileName = "src/Logger/AUTHENTICATION.log";
            FileHandler fileHandler = new FileHandler(loggerFileName, true);
            log.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
