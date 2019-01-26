package de.fhaachen.prozessbereitstellung;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DeleteDeployment implements JavaDelegate{

	public void execute(DelegateExecution execution) throws Exception {
		execution.getProcessEngineServices().getRepositoryService().deleteDeployment((String) execution.getVariable("deploymentId"), true);
	}

}
