package de.fhaachen.prozessbereitstellung;

import java.util.List;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.repository.Deployment;

public class UndeployProcess implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		
		List<Deployment> deployments = execution.getProcessEngineServices().getRepositoryService()
				.createDeploymentQuery().deploymentName((String) execution.getVariable("definitionName")).list();
		
		if(deployments.size() != 0) {
			throw new BpmnError("DeploymentNotPossible");
		}
		
		
	}

}
