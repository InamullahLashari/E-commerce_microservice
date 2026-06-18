package com.example.gateway.filter;

import com.example.gateway.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Order(-100)
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

 private final JwtTokenProvider TokenProvider;
 //private final BlacklistTokenService blacklistTokenService;
 //private final RoutesValidator routesValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,WebFilterChain chain){

        String path = exchange.getRequest().getURI().getPath();

        String token =


    }


    //===================STRICT BEARER TOKEN HELPER FUNCTION======================
    private String extractBearerToken(ServerWebExchange exchange){
        String header = exchange.getRequest()
                .getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
          if(header==null||header.isBlank()){
              return  null;
          }
          if(!header.startsWith("Bearer")){return null;}

          return header.substring(7).trim();
    }

    //===============================Error Response======================
    private Mono<Void> onError(ServerWebExchange exchange,String code,String message){
        log.warn("Auth Failed -> {} : {}",code,message);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);


    }
}