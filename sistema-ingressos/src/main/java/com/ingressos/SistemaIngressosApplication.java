package com.ingressos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemaIngressosApplication {
    public static void main(String[] args) {
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2,TLSv1.3");
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
        System.setProperty("jsse.enableSNIExtension", "true");
        System.setProperty("com.sun.jndi.ldap.object.disableEndpointIdentification", "true");
        SpringApplication.run(SistemaIngressosApplication.class, args);
    }
}
