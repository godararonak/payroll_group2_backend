package com.example.ApiGateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "api/v1/auth/register",
            "/eureka",
             "api/v1/auth/login",
            "/api/v1/auth/resetPassword",
            "api/v1/auth/token_forgot_password",
            "/api/v1/auth/reset_forgotPassword"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
