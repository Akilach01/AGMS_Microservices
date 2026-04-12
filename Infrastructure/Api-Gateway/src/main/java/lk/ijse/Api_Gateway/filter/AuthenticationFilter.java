package lk.ijse.Api_Gateway.filter;

import com.netflix.spectator.impl.Config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;

public class AuthenticationFilter implements GlobalFilter {

    private static final String SECRET ="";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,GatewayFilterChain chain){
       ServerHttpRequest request = exchange.getRequest();

       if(isPublicEndpoint(request.getPath().toString())){
           return chain.filter(exchange);
       }

       if(!request.getHeaders().containsKey("Authorization")){
           return onError(exchange, "No Auth Header", HttpStatus.UNAUTHORIZED);
       }

        String authHeader = request.getHeaders().getOrEmpty("Authorization").get(0);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return onError(exchange, "Invalid Authorization Header", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        try {
            validateToken(token);
        } catch (Exception e) {
            return onError(exchange,"Invalid Token",HttpStatus.UNAUTHORIZED);
        }

        return  chain.filter(exchange);
        }
        private boolean isPublicEndpoint(String path){
        return path.contains("/auth/login") || path.contains("/auth/register");

    }
    public void validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        DataBuffer buffer = response.bufferFactory().wrap(err.getBytes());
        return response.writeWith(Mono.just(buffer));
    }

}
