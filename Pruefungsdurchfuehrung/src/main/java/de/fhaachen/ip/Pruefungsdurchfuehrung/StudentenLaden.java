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

public class StudentenLaden implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {
    	String[][] students = GetStudents.Get(execution.getVariable("modul").toString());
    	
    	HashMap map = new HashMap();

    	//JSONArray jsonArr = new JSONArray();
    	for(int i = 0; i < students.length; i++) {
    		map.put(students[i][0].toString(), "");
    		//JSONObject json = new JSONObject();
    		//json.put("Note","");
    		//json.put("ID",students[i][0]);
    		
    		
    	}
    	
    	JSONObject json = new JSONObject(map);
    	
    	
    	//execution.setVariable("Students", json);
    	execution.setVariable("Students", json.toString());
    	
    }
}