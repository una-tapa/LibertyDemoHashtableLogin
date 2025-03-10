# Sample Hashtable LoginModule

This repository contains a class that serves as an implementation of the custom login module. 

## Creation of a sample custom loginModule jar 

`1.` Update the following file.  
Make the necessary modifications to the SampleCustomLoginModule.java file to reflect the desired behavior. This may involve implementing specific methods, modifying existing functionality, or adding additional logic as required.

This sample loginModule shows how to create a hashtable with specific set of keys. The hashtable tells Liberty to create a user Subject without validating the userid and password against the registry. This function is handy when the user registry is outside the Liberty server. 

./src/main/java/com/ibm/ws/samples/lm/SampleCustomLoginModule.java

`2.` Ensure that you have Maven installed on your system. If not, you can download it from the official Maven website and follow the installation instructions. Open a terminal or command prompt and navigate to the root directory of your project where the pom.xml file is located. Run the following command to build and package the project using Maven:
```
mvn package
```
`3.` After the build process completes successfully, the JAR file will be created in the target directory of your project.
```
./target/samplelm-1.0-SNAPSHOT.jar
```

## How to configure the custom loginModule jar 

The following articles provide instructions on how to configure the JAR file for the WebSphere runtimes. 

- [WebSphere Liberty or OpenLiberty](https://www.ibm.com/docs/en/was-liberty/base?topic=liberty-configuring-jaas-custom-login-module)
- [WebSphere Application Server traditional](https://www.ibm.com/docs/en/was/8.5.5?topic=SSEQTP_8.5.5/com.ibm.websphere.nd.multiplatform.doc/ae/tsec_jaascustlogmod.htm) 

## Liberty configuration snippet 

The following configuration should load the SampleCustomLoginModule class when samplelm-1.0-SNAPSHOT.jar is placed in the same directory as server.xml. 
```
<library id="customLoginLib"> 
    <fileset dir="${server.config.dir}" includes="samplelm-1.0-SNAPSHOT.jar"/> 
</library> 

<jaasLoginModule id="myCustom" 
                 className="com.ibm.ws.samples.lm.SampleCustomLoginModule" 
                 controlFlag="REQUIRED" libraryRef="customLoginLib">
  <options debug="true"/>
</jaasLoginModule>
```
## Sample code for login from Application 

The following sample code is for the applicaton to perform Jaas loginModule to assert a user 

```
       // Provide loginValues via our custom CallbackHandler to assert a user without user registry validation 
        String uniqueId = "jwtUser"
        String securityName = "jwtRealm/jwtUser"; 
        String groups = "group1" 
        CallbackHandler handler = new MyCallbackHandler(uniqueId, securityName, groups);

        // "myCustom" -> The JAAS config entry you defined
        LoginContext lc = new LoginContext("myCustom", handler);

        // Trigger the login process
        lc.login();
```

## License
Unless otherwise noted in a script:<br/>
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
