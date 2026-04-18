package lk.ijse.ZoneService.client;

import org.apache.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
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

public void refreshAccessToken(){
        try {
        Map<String, String> payload = new HashMap<>();
        payload.put("refresh_token",refreshToken);

        Map<String, Object> response = restTemplate.postForObject(BASE_URL+"/auth/refresh",payload,Map.class);

        if(response != null){
            this.accessToken = (String) response.get("access_token");
            log.info("Token refreshed");
        }

        } catch (Exception e) {
            log.error("Refresh failed, try login again: {}", e.getMessage());
            login();
        }
}

public StringregisterDevice(String deviceName, String zoneId){
        ensureAuthnticated();
        try{
            HttpHeaders headers = createAuthHeaders();
            map<String, String> payload = new HashMap<>();
            payload.put("name", deviceName);
            payload.put("zoneId",zoneId);
            HttpEntity<Map<String,String>> entity = new HttpEntity<>(payload, headers);

            ResponseEntity<Map> response = restTemplate.exchange(BASE_URL+"/devices",HttpMethod.POST,entity,Map.class);
            if(response.getBody() != null){
                String deviceId = (String) response.getBody().get("deviceId");
                log.info("device registered successfully: {}", deviceId);
                return deviceId;
            }

            } catch (Exception e) {
            log.error("Device registration failed:{}", e.getMessage());

            try{
                refreshAccessToken();
                HttpHeaders headers = createAuthHeader();

                Map<String, String> payload = new HashMap<>();
                payload.put("name", deviceName);
                payload.put("zoneId",zoneId);

                HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);

                @SuppressWarnings("unchecked")
                ResponseEntity<Map> response = restTemplate.exchange(
                        BASE_URL + "/devices", HttpMethod.POST, entity, Map.class);

                if (response.getBody() != null) {
                    return (String) response.getBody().get("deviceId");
                }

            } catch (Exception ex) {
                log.error("Retry also failed: {}", ex.getMessage());
            }
        }

            }return "device-" + System.currentTimeMillis();
        }
 public Map<String, Object> fetchTeltry(String deviceId){
   ensureAuthenticated();
   try{
       HttpHeaders headers = createAuthHeaders();
       HttpEntity<Void> entity = new HttpEntity<>(headers);

       ResponseEntity<Map> response = restTemplate.exchange(
               BASE_URL + "/devices/telemetry/" + deviceId,
               HttpMethod.GET, entity, Map.class
       );
       return response.getBody();
} catch (Exception e) {
       log.error("Retry failed : {}", ex.getMessage());
   }
}
  return null;
}

private void ensureAuthenticated(){
    if (this.accessToken == null){
        register();
        login();
    }
}
private HttpHeaders createAuthHeaders(){
    httpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(this.accessToken);
    return headers;
  }
}
