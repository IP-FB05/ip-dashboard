package de.fhaachen.ip.kapazitaetsplanung;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.variable.Variables;
import org.json.JSONObject;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class getProfs implements JavaDelegate {

    public void execute(DelegateExecution execution) {
        List<User> users = execution.getProcessEngineServices().getIdentityService().createUserQuery().memberOfGroup("professors").list();

        /*
        for (int i = 0; i < users.size(); i++) {
            execution.setVariable("P" + i, users.get(i).getLastName().toString());
        }
        */

        HashMap map = new HashMap();
        ArrayList<String> profsList = new ArrayList<String>();

        //Studenten in eine Map eintragen
        for(int i = 0; i < users.size(); i++) {
            map.put(i, users.get(i).getLastName().toString());
            profsList.add(users.get(i).getLastName().toString());
        }

        // Map als JSON exportierten
        JSONObject json = new JSONObject(map);

        //JSON als Prozessvariable speichern
        execution.setVariable("profs", Variables.objectValue(json.toString()).serializationDataFormat("application/json").create());
        execution.setVariable("profsList", profsList);
    }
}
