package de.fhaachen.ipdashboard.security.services;

import de.fhaachen.ipdashboard.model.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONArray;
import org.json.JSONObject;

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
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User current = new User();
		current.setUsername(username);
		current.setPassword("demo");
		
		try {
			setUserProfil(current);
			logger.info("Profil initialized.");
		} catch(Exception e) {
			logger.info("Profil initialization failed.");
		}

		return UserPrinciple.build(current);
	}

	public UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException {
	
		User current = new User();
		current.setUsername(username);
		current.setPassword(password);
		
		try {
			setUserProfil(current);
			logger.info("Profil initialized.");
		} catch(Exception e) {
			logger.info("Profil initialization failed.");
		}

		return UserPrinciple.build(current);
	}

	/**
	 * complete the User with all information
	 */
	private void setUserProfil(User user) throws Exception {

		// fetch UserDetails
		String profilUrl = "http://ip-dash.ddnss.ch:8080/engine-rest/user/"+user.getUsername()+"/profile";
		URL obj = new URL(profilUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// Basic Auth for camunda-auth
		String basicAuth = user.getUsername()+":"+user.getPassword();
		byte[] message = (basicAuth).getBytes("UTF-8");
		String encoded = javax.xml.bind.DatatypeConverter.printBase64Binary(message);

		// add request header
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "Basic "+encoded);


		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + profilUrl);
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
		//user.setFirstName(json.getString("firstName"));
		//user.setLastName(json.getString("lastName"));
		user.setName(json.getString("firstName")+" "+json.getString("lastName"));
		user.setEmail(json.getString("email"));
		logger.info(user.toString() + " initialized.");

		// fetch UserGroups
		String groupUrl = "http://ip-dash.ddnss.ch:8080/engine-rest/identity/groups?userId="+user.getUsername();
		URL groupObj = new URL(groupUrl);
		con = (HttpURLConnection) groupObj.openConnection();

		// add request header
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "Basic "+encoded);

		responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " +groupUrl);
		System.out.println("Response Code : " + responseCode);

		in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
        in.close();
        
        jsonString = response.toString();
		json = new JSONObject(jsonString);
		JSONArray jArray = (JSONArray) json.getJSONArray("groups");
		String[] groups = new String[jArray.length()];
		for(int i=0; i<jArray.length(); i++) {
			JSONObject currentGroup = jArray.getJSONObject(i);
			groups[i] = currentGroup.getString("name");
		}
		user.setGroups(groups);
		logger.info("Groups initialized.");
	}	
}