package de.fhaachen.deploywf;

import java.util.Date;
import java.util.List;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;

public class CheckCamunda implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Date fiveMinutesBack = new Date(System.currentTimeMillis() - 300 * 1000);
		List<Deployment> deployments = execution.getProcessEngineServices().getRepositoryService()
				.createDeploymentQuery().deploymentAfter(fiveMinutesBack)
				.deploymentName((String) execution.getVariable("definitionName")).list();

		if (deployments.size() >= 1) { // Deployment vorhanden?
			String deploymentId = deployments.get(0).getId();
			execution.setVariable("deploymentId", deploymentId);

			ProcessDefinition definition;
			try {
				definition = execution.getProcessEngineServices().getRepositoryService().createProcessDefinitionQuery()
						//.processDefinitionName((String) execution.getVariable("definitionName"))
						.deploymentId(deploymentId).orderByVersionTag().desc().list().get(0);
				execution.setVariable("processDefinitionID", definition.getId());
				execution.setVariable("camundasuccess", true);
			} catch (ProcessEngineException e) {
				// keine Definition -> Fehler!
				execution.setVariable("camundasuccess", false);
				execution.setVariable("errorlog", "Deployment vorhanden, aber keine Prozessdefinition gefunden");
			} finally {

			}

		} else {
			// kein Deployment -> Fehler!
			execution.setVariable("errorlog", "Deployment nicht vorhanden");
			execution.setVariable("camundasuccess", false);
		}

	}
}
