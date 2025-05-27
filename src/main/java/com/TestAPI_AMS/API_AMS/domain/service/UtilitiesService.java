package com.TestAPI_AMS.API_AMS.domain.service;

import com.TestAPI_AMS.API_AMS.custom.exceptions.CustomBadRequestException;
import com.TestAPI_AMS.API_AMS.custom.exceptions.CustomForbiddenException;
import com.TestAPI_AMS.API_AMS.custom.exceptions.CustomServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UtilitiesService {

    public final static String HASH_FOR_PASSWORDS = "IMSYSL33TSUPAH4X0RSALTFORPASSWORDS";

    public static String calcularHash(String texto) {
        final String SALT = HASH_FOR_PASSWORDS;
        final String CODIFICACION = "UTF-8";
        final String ALGORITMO = "SHA-512";

        String hashCalculado = "";
        if(texto != null){
            try {

                MessageDigest messageDigest = MessageDigest.getInstance(ALGORITMO);
                messageDigest.update(SALT.getBytes(CODIFICACION));
                byte[] bytes = messageDigest.digest(texto.getBytes(CODIFICACION));

                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < bytes.length; i++) {
                    stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                hashCalculado = stringBuilder.toString();
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {

            }
        }
        return hashCalculado;
    }
}

