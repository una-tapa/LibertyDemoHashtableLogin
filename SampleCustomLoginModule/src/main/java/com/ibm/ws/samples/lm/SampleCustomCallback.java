package com.ibm.ws.samples.lm;

import javax.security.auth.callback.Callback;

public class SampleCustomCallback  implements Callback
{
    String uniqueId; 
    String securityName;
    String groups;

    public String getUniqueId() {
        return uniqueId;
    }
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    // Getter and Setter for securityName
    public String getSecurityName() {
        return securityName;
    }
    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    // Getter and Setter for groups
    public String getGroups() {
        return groups;
    }
    public void setGroups(String groups) {
        this.groups = groups;
    }
    
}
