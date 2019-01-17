package utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import utils.GetStudents;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetStudents {
    public static String[][] Get(String modul){
    	
        //Initialisiere REST call
        Client client = Client.create();

        //führe den REST call aus
        String restcall = "http://localhost:8888/pruefung/studentList/" + modul;
        WebResource webResource = client.resource(restcall);

        //Speichere die Entgegengenommenden daten
        ClientResponse response = webResource.accept("application/json").header("Authorization", "Basic ZGVtbzpkZW1v").get(ClientResponse.class);


        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        String output = response.getEntity(String.class);

        JSONArray array = new JSONArray(output);

        String[][] students = new String[array.length()][3];
        for (int i = 0; i < array.length(); i++) {
            JSONObject row = array.getJSONObject(i);
            students[i][0] = Integer.toString(row.getInt("id"));
            students[i][1] = row.getString("name");
            students[i][2] = row.getString("email");
        }

        return students;
    }
}
