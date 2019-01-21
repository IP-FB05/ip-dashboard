package de.fhaachen.pruefungsdurchfuehrung;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import utils.GetStudents;

import java.util.*;
import org.json.JSONObject;
import org.camunda.bpm.engine.variable.*;

public class LoadStudentls implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		//Studenten laden
		String[][] students = GetStudents.get(execution.getVariable("modul").toString());

		HashMap map = new HashMap();

		//Studenten in eine Map eintragen
		for(int i = 0; i < students.length; i++) {
			map.put(students[i][0].toString(), "");
		}

		// Map als JSON exportierten
		JSONObject json = new JSONObject(map);

		//JSON als Prozessvariable speichern
		execution.setVariable("studGradesJSON", Variables.objectValue(json.toString()).serializationDataFormat("application/json").create());
		execution.setVariable("studRESTRe",students);
		execution.setVariable("studNumber",students.length);
	}
}