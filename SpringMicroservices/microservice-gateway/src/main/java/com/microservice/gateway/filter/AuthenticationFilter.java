package com.microservice.gateway.filter;

import com.microservice.gateway.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private RouteValidator validator;

    //@Autowired
    //private RestTemplate restTemplate;

    @Autowired
    private JWTUtil jwtUtil;

    public AuthenticationFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if(validator.isSecured.test(exchange.getRequest())){
                // header contains token or not
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("falta authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }

                try {
                    // REST call to AUTHENTICATION microservice
                    // restTemplate.getForObject("http://localhost:8091/validate?token" + authHeader, String.class);
                    jwtUtil.validateToken(authHeader);
                } catch(Exception e){
                    System.out.println("acceso invalido");
                    throw new RuntimeException("acceso no autorizado a la aplicación");
                }
            }

            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
