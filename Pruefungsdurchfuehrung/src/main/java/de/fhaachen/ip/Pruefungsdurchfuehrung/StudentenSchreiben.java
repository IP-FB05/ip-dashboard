package de.fhaachen.ip.Pruefungsdurchfuehrung;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import utils.Config;
import utils.GetStudents;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class StudentenSchreiben implements JavaDelegate {


    public void execute(DelegateExecution execution) throws Exception {
		String students = execution.getVariable("Students").toString();
    	String modul = execution.getVariable("modul").toString();
		
		
		JSONObject json = new JSONObject(students);

		String[][] studentsList = GetStudents.Get(execution.getVariable("modul").toString());
        for (int i = 0; i < json.length(); i++) {
        	String ID = studentsList[i][0];
            execution.setVariable(ID, json.getString(ID));
            
        	
            Client client = Client.create();
            String restcall = "http://localhost:8888/pruefungBenotung/" + ID + "/" + modul + "/" + json.getString(ID);
            WebResource webResource = client.resource(restcall);
            ClientResponse response = webResource.accept("application/json").header("Authorization", "Basic ZGVtbzpkZW1v").get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

        }

    }
}