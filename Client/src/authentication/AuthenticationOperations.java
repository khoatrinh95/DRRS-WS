
package authentication;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "AuthenticationOperations", targetNamespace = "http://Authentication/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AuthenticationOperations {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "authorize", targetNamespace = "http://Authentication/", className = "authentication.Authorize")
    @ResponseWrapper(localName = "authorizeResponse", targetNamespace = "http://Authentication/", className = "authentication.AuthorizeResponse")
    @Action(input = "http://Authentication/AuthenticationOperations/authorizeRequest", output = "http://Authentication/AuthenticationOperations/authorizeResponse")
    public int authorize(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        boolean arg1);

}
