package de.fhaachen.prozessbereitstellung;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DeleteDeployment implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		execution.getProcessEngineServices().getRepositoryService().deleteDeployment((String) execution.getVariable("deploymentId"), true);
	}

}
