package com.example.authprototype.security;


import lombok.extern.java.Log;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


@Log
@Component
public class JwtKeysProvider {

    @Value("classpath:/keys/public.pem")
    private Resource publicKeyResource;
    @Value("classpath:/keys/private.pem")
    private Resource privateKeyResource;

    public RSAPublicKey getRSAPublicKey () throws Exception {
        String key = new String(Files.readAllBytes(publicKeyResource.getFile().toPath()), StandardCharsets.UTF_8);
        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.decodeBase64(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public RSAPrivateKey getRSAPrivateKey() throws Exception {
        String key = new String(Files.readAllBytes(privateKeyResource.getFile().toPath()), StandardCharsets.UTF_8);
//        System.out.println(key);
        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.decodeBase64(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
}
