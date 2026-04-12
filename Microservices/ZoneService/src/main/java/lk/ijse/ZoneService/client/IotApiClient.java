package lk.ijse.ZoneService.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Sl4j
public class IotApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";

    private static final String USERNAME = "agms_admin";
    private static final String PASSWORD = "1234";

    private final RestTemplate restTemplate = new RestTemplate();

    private String accessToken;
    private String refreshToken;

    public void register(){
        try {
            Map<String, String> payload = new HashMap<>();
            payload.put("username", USERNAME);
            payload.put("password",PASSWORD);

            restTemplate.postForObject(BASE_URL+"/auth/register",payload,Map.class);
            log.info("User registered to Iot Api");
        }catch(Exception e){
            log.warn("Usre might be already registered: {}",e.getMessage());
        }
    }

    public void login(){
        try {
            Map<String, String> payload = new HashMap<>();
            payload.put("username", USERNAME);
            payload.put("password",PASSWORD);

            Map<String, Object> response = restTemplate.postForObject(BASE_URL+"/auth/login",payload,Map.class);

            if(response != null){
                this.accessToken = (String) response.get("access_token");
                this.refreshToken = (String) response.get("refresh_token");
                log.info("Login successful, tokens recieved");
            }

        } catch (Exception e) {
            log.error("Login failed: {}" , e.getMessage());
        }
    }


}
