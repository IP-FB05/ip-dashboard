package de.fhaachen.deploywf;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SetAuthentications implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {

		// get Authorization Service
		AuthorizationService authService = execution.getProcessEngineServices().getAuthorizationService();

		// check if Authorization is set
		if (authService.createAuthorizationQuery().resourceId((String) execution.getVariable("definitionId"))
				.userIdIn((String) execution.getVariable("user")).list().size() == 0) {

			// create new Authorizations
			Authorization newAuth = authService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);

			// set Resource Type
			newAuth.setResourceType(Resources.PROCESS_DEFINITION.resourceType());

			// set Process as Ressource
			newAuth.setResourceId((String) execution.getVariable("definitionId"));

			// set creator as User only
			newAuth.setUserId((String) execution.getVariable("user"));

			// add permissions
			newAuth.addPermission(Permissions.READ_INSTANCE);
			newAuth.addPermission(Permissions.UPDATE_INSTANCE);
			newAuth.addPermission(Permissions.CREATE_INSTANCE);
			newAuth.addPermission(Permissions.TASK_WORK);
			newAuth.addPermission(Permissions.UPDATE_TASK);

			// save Authorization
			authService.saveAuthorization(newAuth);
		}

	}

}
