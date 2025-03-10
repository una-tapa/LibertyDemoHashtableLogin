package com.ibm.ws.samples.lm;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class SampleCustomCallbackHandler implements CallbackHandler {

    private final String uniqueId;
    private final String securityName;
    private final String groups;

    public SampleCustomCallbackHandler(String uniqueId, String securityName, String groups) {
        this.uniqueId = uniqueId;
        this.securityName = securityName;
        this.groups = groups;
    }

    @Override
    public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof SampleCustomCallback) {
                SampleCustomCallback sampleCCB = (SampleCustomCallback) callback;
                sampleCCB.setUniqueId(uniqueId);
                sampleCCB.setSecurityName(securityName);
                sampleCCB.setGroups(groups);
            } else {
                throw new UnsupportedCallbackException(callback);
            }
        }

    }
}
