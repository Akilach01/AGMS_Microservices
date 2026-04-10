package lk.ijse.Api_Gateway.filter;

import com.netflix.spectator.impl.Config;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config){
        return ((exchange, chain) ->{
            if(validator.isSecured.test(exchange.getRequest())){


            }
        })
    }



}
