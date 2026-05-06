package com.bitsandbytes.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {
    private static final String REQUEST_ID_HEADER = "X-Request-ID";
    private static final String START_TIME_ATTR = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String requestId = getOrGenerateRequestId(exchange);

        long startTime = System.currentTimeMillis();

        MDC.put("requestId",requestId);


        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header(REQUEST_ID_HEADER, requestId)
                .build();


        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();


        log.info("Incoming Request | method={} | path={} | ip={} | requestId={}",
                mutatedRequest.getMethod(),
                mutatedRequest.getPath(),
                mutatedRequest.getRemoteAddress(),
                requestId
        );


        return chain.filter(mutatedExchange)
                .doFinally(signal ->MDC.clear());
    }

    //===========================ReQuest ID handleing=======================

    private String getOrGenerateRequestId(ServerWebExchange exchange) {

        String headerRequestId = exchange.getRequest()
                .getHeaders()
                .getFirst(REQUEST_ID_HEADER);

        return (headerRequestId != null && !headerRequestId.isEmpty())
                ? headerRequestId
                : UUID.randomUUID().toString();
    }



//========================================Order execute========================================
    @Override
    public int getOrder() {
        return -100;
    }
}
