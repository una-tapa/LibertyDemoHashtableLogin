/*******************************************************************************
   This is a sample JAAS LoginModule
 *******************************************************************************/
package com.ibm.ws.samples.lm;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import com.ibm.wsspi.security.token.AttributeNameConstants; 

/**
 *
 */
public class SampleCustomLoginModule implements LoginModule {

    private CallbackHandler callbackHandler;
    private boolean succeeded;
    // com.ibm.websphere.security.cred.WSCredential credential = null;
    // com.ibm.websphere.security.auth.WSPrincipal principal = null;
    private javax.security.auth.Subject _subject;
    private javax.security.auth.callback.CallbackHandler _callbackHandler;
    private java.util.Map _sharedState;
    private java.util.Map _options;

    /** {@inheritDoc} */
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.callbackHandler = callbackHandler;

        _subject = subject;
        _callbackHandler = callbackHandler;
        _sharedState = sharedState;
        _options = options;

        System.out.println("DEBUG: Initializing SampleCustomLoginModule!!");
        if (subject != null) {
            System.out.println("DEBUG: Input Subject=" + subject.toString());
        } else {
            System.out.println("DEBUG: Input subject is null");
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean login() throws LoginException {
        boolean succeeded = false;

        SampleCustomCallback mySCCB = new SampleCustomCallback();
        Callback[] callbacks = new Callback[] { mySCCB }; 

        String uniqueid = "";
        String securityName = "";
        String groupList = "";

        try {
            _callbackHandler.handle(callbacks);
            uniqueid = mySCCB.getUniqueId();
            securityName = mySCCB.getSecurityName();
            groupList = mySCCB.getGroups();

            java.util.Hashtable hashtable = new java.util.Hashtable();
            hashtable.put(com.ibm.wsspi.security.token.AttributeNameConstants.WSCREDENTIAL_UNIQUEID, uniqueid);
            hashtable.put(com.ibm.wsspi.security.token.AttributeNameConstants.WSCREDENTIAL_SECURITYNAME, securityName);
            hashtable.put(com.ibm.wsspi.security.token.AttributeNameConstants.WSCREDENTIAL_GROUPS, groupList);
            hashtable.put(com.ibm.wsspi.security.token.AttributeNameConstants.WSCREDENTIAL_CACHE_KEY, "myCustomAttribute" + uniqueid);

            System.out.println("DEBUG: login()  hashtable=" + hashtable.toString());

            // Adds the hashtable to the sharedState of the Subject.
            _sharedState.put(com.ibm.wsspi.security.token.AttributeNameConstants.WSCREDENTIAL_PROPERTIES_KEY, hashtable);

        } catch (IOException ie) {
           System.out.println("IOException received." + exceptionDetails(ie)); 
        }
        catch (UnsupportedCallbackException usce) {
            System.out.println("UnsupportedCallbackException received." + exceptionDetails(usce)); 
        }
        catch (Exception e) {
            // Other exception
            System.out.println(exceptionDetails(e));
            throw e;
        }
        succeeded = true; 

        return succeeded;
    }

    public static String exceptionDetails(Throwable t) {
        StringBuffer sb = new StringBuffer("-- Error Stack ---");
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        t.printStackTrace(new java.io.PrintStream(baos));
        sb.append("\n");
        sb.append(baos);
        sb.append("--- END Error Stack ---");
        return sb.toString();
    }

    /** {@inheritDoc} */
    @Override
    public boolean commit() throws LoginException {
        if (succeeded == false) {
            System.out.println("DEBUG: do not commit");
            return false;
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean abort() throws LoginException {
        // TODO Auto-generated method stub
        if (succeeded == false) {
            System.out.println("DEBUG: abort = true");
            return true;
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean logout() throws LoginException {
        System.out.println("DEBUG: logout was called");
        // TODO Auto-generated method stub
        return false;
    }

    private Callback[] getCallbacks(CallbackHandler callbackHandler) throws IOException, UnsupportedCallbackException {
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("Username: ");
        callbacks[1] = new PasswordCallback("Password: ", false);

        callbackHandler.handle(callbacks);
        return callbacks;
    }
}
