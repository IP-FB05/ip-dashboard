package de.fhaachen.prozessbereitstellung;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

<<<<<<< HEAD
import java.util.List;
=======
import java.util.ArrayList;
import java.util.Collection;

>>>>>>> branch 'master' of https://github.com/IP-FB05/ip-dashboard.git

public class DBAuthentication implements JavaDelegate {


    public void execute(DelegateExecution execution){                            

			// get Authentication Service
			AuthorizationService authService = execution.getProcessEngineServices().getAuthorizationService();
			
			// get groups
			@SuppressWarnings("unchecked")
			List<String> groups = (List<String>) execution.getVariable("groups");
			Authorization newAuth;
			
			String groupString = "";
			
			for (String group : groups) {
				// create Authorization
				newAuth = authService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
				
				// set Resource Type
				newAuth.setResourceType(Resources.PROCESS_DEFINITION.resourceType());

				// set Process as Ressource
				newAuth.setResourceId((String) execution.getVariable("definitionId"));
				
				// set group
				newAuth.setGroupId(group);
				
				// add permissions
//				newAuth.addPermission(Permissions.READ_INSTANCE);
//				newAuth.addPermission(Permissions.UPDATE_INSTANCE);
//				newAuth.addPermission(Permissions.CREATE_INSTANCE);
//				newAuth.addPermission(Permissions.TASK_WORK);
//				newAuth.addPermission(Permissions.UPDATE_TASK);
				newAuth.addPermission(Permissions.ALL);
				
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