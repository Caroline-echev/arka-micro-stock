package com.arka.micro_stock.adapters.driven.security;

import com.arka.micro_stock.domain.exception.UnauthorizedException;
import com.arka.micro_stock.domain.spi.IAuthenticationPersistencePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

import static com.arka.micro_stock.adapters.util.CountryConstantsDriving.NO_AUTHENTICATION_FOUND;
import static com.arka.micro_stock.adapters.util.CountryConstantsDriving.NO_JWT_TOKEN_AVAILABLE;
import static com.arka.micro_stock.configuration.util.ConstantsConfiguration.BEARER;

@Slf4j
public class AuthenticationAdapter implements IAuthenticationPersistencePort {

    @Override
    public Mono<String> getAuthorizationHeader() {
        return ReactiveSecurityContextHolder.getContext()
                .doOnSubscribe(subscription -> log.debug("Attempting to extract authentication from security context"))
                .map(securityContext -> securityContext.getAuthentication())
                .cast(Authentication.class)
                .flatMap(authentication -> {
                    log.debug("Authentication found: {}", authentication.getName());

                    return Mono.deferContextual(ctx -> {
                        if (ctx.hasKey("jwt-token")) {
                            String token = ctx.get("jwt-token");
                            log.debug("JWT token found in context: {}", token);
                            return Mono.just(BEARER + " " + token);
                        }

                        Object credentials = authentication.getCredentials();
                        if (credentials instanceof String token) {
                            log.debug("JWT token found in authentication credentials");
                            return Mono.just(BEARER + " " + token);
                        }

                        log.warn("No JWT token available in context or credentials");
                        return Mono.error(new UnauthorizedException(NO_JWT_TOKEN_AVAILABLE));
                    });
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("No authentication found in ReactiveSecurityContext");
                    return Mono.error(new UnauthorizedException(NO_AUTHENTICATION_FOUND));
                }));
    }

    @Override
    public Mono<String> getAuthenticatedEmail() {
        log.info("Attempting to retrieve authenticated email from security context");
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> {
                    Authentication auth = securityContext.getAuthentication();
                    log.debug("Authentication object: {}", auth);
                    return auth;
                })
                .map(Authentication::getPrincipal)
                .cast(String.class)
                .doOnSuccess(email -> log.info("Authenticated email retrieved: {}", email))
                .doOnError(error -> log.error("Error retrieving authenticated email: {}", error.getMessage()));
    }
}
