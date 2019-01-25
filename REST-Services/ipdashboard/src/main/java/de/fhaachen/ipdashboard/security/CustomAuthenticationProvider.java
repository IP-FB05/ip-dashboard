package de.fhaachen.ipdashboard.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import javax.annotation.PostConstruct;

import org.json.JSONObject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.BadCredentialsException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import de.fhaachen.ipdashboard.model.User;
 
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
 
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	List<User> users = new ArrayList<User>();
  
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
         
        boolean authenticated;

        try {
            authenticated = this.isAuthenticated(name,password);
        } catch (Exception e) {
            authenticated = false;
            e.printStackTrace();
        }

		if (!authenticated) {
			logger.error("Authentication failed for user = " + name);
			throw new BadCredentialsException("Authentication failed for user = " + name);
		}
 
		// find out the exited users
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(name, password,
				grantedAuthorities);
 
		logger.info("Succesful Authentication with user = " + name);
		return auth;
	}
 
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    // Methode für HTTP Request an Camunda Authenticate Service
	private boolean isAuthenticated(String username, String password) throws Exception {

		String url = "http://ip-dash.ddnss.ch:8080/engine-rest/identity/verify";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		final String POST_PARAMS = "{\n" + "   \"username\": \""+username+"\",\n" + "    \"password\": \""+password+"\"  \n}";

		// add request header
		con.setRequestMethod("POST");
		//con.setRequestProperty("User-Agent", "Mozilla/5.0");
		//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json");

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(POST_PARAMS);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + POST_PARAMS);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
        in.close();
        
        String jsonString = response.toString();
        JSONObject json = new JSONObject(jsonString);
        boolean isAuthenticated = (boolean) json.get("authenticated");


        return isAuthenticated;
	}

}