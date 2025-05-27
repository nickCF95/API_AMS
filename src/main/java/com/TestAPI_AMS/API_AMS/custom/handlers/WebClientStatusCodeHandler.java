package com.TestAPI_AMS.API_AMS.custom.handlers;

import com.TestAPI_AMS.API_AMS.custom.exceptions.CustomBadRequestException;
import com.TestAPI_AMS.API_AMS.custom.exceptions.CustomForbiddenException;
import com.TestAPI_AMS.API_AMS.custom.exceptions.CustomServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

public class WebClientStatusCodeHandler {
    public static Mono<ClientResponse> exchangeFilterResponseProcessor(ClientResponse response) {
        HttpStatusCode status = response.statusCode();
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            return response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new CustomServerErrorException(body)));
        }
        if (HttpStatus.BAD_REQUEST.equals(status)) {
            return response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new CustomBadRequestException(body)));
        }
        if (HttpStatus.FORBIDDEN.equals(status)) {
            return response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new CustomForbiddenException(body)));
        }
        return Mono.just(response);
    }
}
