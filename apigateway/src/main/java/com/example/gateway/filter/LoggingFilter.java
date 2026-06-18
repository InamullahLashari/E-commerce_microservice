package com.example.gateway.filter;

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

        // 1. Generate or reuse request ID
        String requestId = getOrGenerateRequestId(exchange);

        // 2. Start time for latency tracking
        long startTime = System.currentTimeMillis();

        // 3. Put requestId in MDC (VERY IMPORTANT for production logging)
        MDC.put("requestId", requestId);

        // 4. Add request ID into request headers (proper immutable way)
        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header(REQUEST_ID_HEADER, requestId)
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();

        // 5. Log incoming request (structured style)
        log.info("Incoming Request | method={} | path={} | ip={} | requestId={}",
                mutatedRequest.getMethod(),
                mutatedRequest.getPath(),
                mutatedRequest.getRemoteAddress(),
                requestId
        );

        // 6. Store start time in attributes (reactive safe)
        mutatedExchange.getAttributes().put(START_TIME_ATTR, startTime);

        // 7. Continue filter chain
        return chain.filter(mutatedExchange)
                .doOnSuccess(aVoid -> handleSuccess(mutatedExchange, requestId))
                .doOnError(error -> handleError(requestId, error))
                .doFinally(signal -> MDC.clear());
    }

    // ---------------- SUCCESS HANDLING ----------------
    private void handleSuccess(ServerWebExchange exchange, String requestId) {

        Long startTime = exchange.getAttribute(START_TIME_ATTR);
        long duration = startTime != null ? System.currentTimeMillis() - startTime : -1;

        log.info("Completed Request | requestId={} | status={} | duration={}ms",
                requestId,
                exchange.getResponse().getStatusCode(),
                duration
        );
    }

    // ---------------- ERROR HANDLING ----------------
    private void handleError(String requestId, Throwable error) {

        log.error("Failed Request | requestId={} | error={}",
                requestId,
                error.getMessage(),
                error
        );
    }

    // ---------------- REQUEST ID HANDLING ----------------
    private String getOrGenerateRequestId(ServerWebExchange exchange) {

        String headerRequestId = exchange.getRequest()
                .getHeaders()
                .getFirst(REQUEST_ID_HEADER);

        return (headerRequestId != null && !headerRequestId.isEmpty())
                ? headerRequestId
                : UUID.randomUUID().toString();
    }

    // ---------------- ORDER ----------------
    @Override
    public int getOrder() {
        return -100; // high priority but safe
    }
}