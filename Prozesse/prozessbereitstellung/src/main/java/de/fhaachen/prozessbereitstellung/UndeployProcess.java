package de.fhaachen.prozessbereitstellung;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.repository.Deployment;

public class UndeployProcess implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		List<Deployment> deployments = execution.getProcessEngineServices().getRepositoryService()
				.createDeploymentQuery().deploymentName((String) execution.getVariable("definitionName")).list();
		
		for(Deployment deployment : deployments) {
			execution.getProcessEngineServices().getRepositoryService().deleteDeployment(deployment.getId());
			
			// TODO: Optimierung -> zugehörige Authorizations löschen
		}
		
		
	}

}
