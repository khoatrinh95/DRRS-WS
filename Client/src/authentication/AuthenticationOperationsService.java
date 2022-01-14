
package authentication;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "AuthenticationOperationsService", targetNamespace = "http://Authentication/", wsdlLocation = "file:/Users/khoatrinh/Documents/SOFTWARE%20ENGINEER/YEAR%203.1/SOEN423%20-%20Distributed%20Systems/Assignments/3/Client/src/authentication.wsdl")
public class AuthenticationOperationsService
    extends Service
{

    private final static URL AUTHENTICATIONOPERATIONSSERVICE_WSDL_LOCATION;
    private final static WebServiceException AUTHENTICATIONOPERATIONSSERVICE_EXCEPTION;
    private final static QName AUTHENTICATIONOPERATIONSSERVICE_QNAME = new QName("http://Authentication/", "AuthenticationOperationsService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/Users/khoatrinh/Documents/SOFTWARE%20ENGINEER/YEAR%203.1/SOEN423%20-%20Distributed%20Systems/Assignments/3/Client/src/authentication.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        AUTHENTICATIONOPERATIONSSERVICE_WSDL_LOCATION = url;
        AUTHENTICATIONOPERATIONSSERVICE_EXCEPTION = e;
    }

    public AuthenticationOperationsService() {
        super(__getWsdlLocation(), AUTHENTICATIONOPERATIONSSERVICE_QNAME);
    }

    public AuthenticationOperationsService(WebServiceFeature... features) {
        super(__getWsdlLocation(), AUTHENTICATIONOPERATIONSSERVICE_QNAME, features);
    }

    public AuthenticationOperationsService(URL wsdlLocation) {
        super(wsdlLocation, AUTHENTICATIONOPERATIONSSERVICE_QNAME);
    }

    public AuthenticationOperationsService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, AUTHENTICATIONOPERATIONSSERVICE_QNAME, features);
    }

    public AuthenticationOperationsService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AuthenticationOperationsService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AuthenticationOperations
     */
    @WebEndpoint(name = "AuthenticationOperationsPort")
    public AuthenticationOperations getAuthenticationOperationsPort() {
        return super.getPort(new QName("http://Authentication/", "AuthenticationOperationsPort"), AuthenticationOperations.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AuthenticationOperations
     */
    @WebEndpoint(name = "AuthenticationOperationsPort")
    public AuthenticationOperations getAuthenticationOperationsPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://Authentication/", "AuthenticationOperationsPort"), AuthenticationOperations.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AUTHENTICATIONOPERATIONSSERVICE_EXCEPTION!= null) {
            throw AUTHENTICATIONOPERATIONSSERVICE_EXCEPTION;
        }
        return AUTHENTICATIONOPERATIONSSERVICE_WSDL_LOCATION;
    }

}
