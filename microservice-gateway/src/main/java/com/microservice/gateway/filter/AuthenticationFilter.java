package com.microservice.gateway.filter;

import com.microservice.gateway.error.NoAuthException;
import com.microservice.gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    public RouteValidator validator;

/*    @Autowired
    public RestTemplate template;*/

    @Autowired
    public JwtUtil util;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())){
                //Header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    try {
                        throw new NoAuthException("No se encontró el token!");
                    } catch (NoAuthException e) {
                        throw new RuntimeException(e);
                    }
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader!=null && authHeader.startsWith("Bearer ")){
                    authHeader=authHeader.substring(7);
                }
                try{
                    //REST call to AUTH service
                    //template.getForObject("http://localhost:9080/validate?token"+authHeader,String.class);
                    util.validateToken(authHeader);
                }catch (Exception e){
                    System.out.println("Invalid access...");
                    try {
                        throw new NoAuthException("Acceso no autorizado, token invalido");
                    } catch (NoAuthException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
