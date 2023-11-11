package api2;

import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import api2.service.UserService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import api2.entity.UserName;

@RestController
public class ApiController {

	@PostMapping(path = "/get/user")
	public String getUserDetails(@RequestBody UserName username) throws Exception{
		String keycloak_host = System.getenv("KEYCLOAK_AUTH_SERVER_URL");
		String keycloak_username = System.getenv("KEYCLOAK_USERNAME");
		String keycloak_password = System.getenv("KEYCLOAK_PASSWORD");
		String url = keycloak_host+"/realms/sso-login/protocol/openid-connect/token";
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setDoOutput(true);

		String params = "username="+keycloak_username+"&password="+keycloak_password+"&client_id=admin-cli&grant_type=password";
		try (DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
                dos.write(params.getBytes(StandardCharsets.UTF_8));
            }
		catch (Exception e) {
            e.printStackTrace();
        }
		connection.connect();
		int responseCode = connection.getResponseCode();
		String access_token="";

		if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, String> jsonMap = objectMapper.readValue(response.toString(), new TypeReference<Map<String, String>>() {});
				access_token = (String) jsonMap.get("access_token");
            } else {
                System.out.println("HTTP POST request failed.");
            }

            // Disconnect the connection
            connection.disconnect();

		url = keycloak_host+"/admin/realms/sso-login/users";
		params = "?username="+username.username;

		connection = (HttpURLConnection) new URL(url+params).openConnection();

		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", "Bearer "+access_token);
		connection.setDoOutput(true);
		connection.connect();
		responseCode = connection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
				return response.toString();
            } else {
				return "Error";
            }
	}

	@PreAuthorize("hasRole('admin-access')")
	@GetMapping(path = "/check/admin")
	public int mod() {
        return 1;
	}
	
	@GetMapping(path = "/anon")
	public String anon() {
		return "Hello! This page is accessible to everyone";
	}
}