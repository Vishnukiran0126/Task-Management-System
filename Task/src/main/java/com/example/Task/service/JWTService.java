package com.example.Task.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    public String secretKey="";

    public JWTService(){
        try{
            KeyGenerator keyGen =KeyGenerator.getInstance("HmacSHA256");//getting a KeyGenerator Instance
            SecretKey sk= keyGen.generateKey();//SecretKey is type and key is generated
            secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());//convert into string and store
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username,String role){
        Map<String,Object> claims =new HashMap<>(); //need to stufy this
        claims.put("role", role);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getKey())
                .compact();
    }

    public Key getKey(){

        //byte[] array to String -> encode
        //String to byte[] -> decode
        byte[] bytes= Decoders.BASE64.decode(secretKey);//convert into bytes
        return Keys.hmacShaKeyFor(bytes);// pass the bytes array key and get the SeretKey
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username);
    }

}
