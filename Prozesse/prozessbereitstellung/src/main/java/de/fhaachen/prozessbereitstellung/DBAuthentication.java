package de.fhaachen.prozessbereitstellung;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import utils.*;

public class DBAuthentication implements JavaDelegate {


    public void execute(DelegateExecution execution){                            

			  //////////////////////////////////////////////////
			 // Ab hier dann Camunda Authorization hinzufügen//
			//////////////////////////////////////////////////
			
			// get Authentication Service
			AuthorizationService authService = execution.getProcessEngineServices().getAuthorizationService();
			
			// get groups
			Collection<String> groups = new ArrayList<String>();
			Authorization newAuth;
			
			String groupString = "";
			
			for (String group : groups) {
				// create Authorization
				newAuth = authService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
				
				// set Process as Ressource
				newAuth.setResourceId((String) execution.getVariable("definitionId"));
				
				// set group
				newAuth.setGroupId(group);
				
				// add permissions
				newAuth.addPermission(Permissions.READ_INSTANCE);
				newAuth.addPermission(Permissions.UPDATE_INSTANCE);
				newAuth.addPermission(Permissions.CREATE_INSTANCE);
				newAuth.addPermission(Permissions.TASK_WORK);
				newAuth.addPermission(Permissions.UPDATE_TASK);
				
				// save Authorization
				authService.saveAuthorization(newAuth);
				
				groupString = groupString + group + ",";
				
			}
			
			if(!groupString.isEmpty()) {
				groupString.substring(0, groupString.length() - 1);
			}
			else {
				groupString = "admin,gast,mitarbeiter,professor,pruefungsamt,student";
			}
			
			execution.setVariable("groupString", groupString);

	}

}