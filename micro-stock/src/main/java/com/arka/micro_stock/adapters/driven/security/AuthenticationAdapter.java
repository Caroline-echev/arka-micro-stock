    package com.arka.micro_stock.adapters.driven.security;

    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.ReactiveSecurityContextHolder;
    import reactor.core.publisher.Mono;

    import static com.arka.micro_stock.configuration.util.ConstantsConfiguration.BEARER;

    public class AuthenticationAdapter {
        public static Mono<String> getAuthorizationHeader() {
            return ReactiveSecurityContextHolder.getContext()
                    .map(securityContext -> securityContext.getAuthentication())
                    .cast(Authentication.class)
                    .flatMap(authentication -> {
                        // First try to get from context
                        return Mono.deferContextual(ctx -> {
                            if (ctx.hasKey("jwt-token")) {
                                String token = ctx.get("jwt-token");
                                return Mono.just(BEARER + " " + token);
                            }
                            // Fallback to authentication credentials
                            Object credentials = authentication.getCredentials();
                            if (credentials instanceof String) {
                                return Mono.just(BEARER + " " + credentials);
                            }
                            return Mono.error(new RuntimeException("No JWT token available"));
                        });
                    })
                    .switchIfEmpty(Mono.error(new RuntimeException("No authentication found")));
        }

    }
