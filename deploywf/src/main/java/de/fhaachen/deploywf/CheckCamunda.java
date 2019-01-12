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
		// TODO
		Date oneMinuteBack = new Date(System.currentTimeMillis() - 90 * 1000);
		List<Deployment> deployments = execution.getProcessEngineServices().getRepositoryService()
				.createDeploymentQuery().deploymentAfter(oneMinuteBack)
				.deploymentName(execution.getVariable("definitionId") + "%").list();

		if (deployments.size() >= 1) { // Deployment vorhanden?
			String deploymentId = deployments.get(0).getId();
			execution.setVariable("deploymentId", deploymentId);
			
			
			ProcessDefinition definition;
			try {
				definition = execution.getProcessEngineServices().getRepositoryService()
						.createProcessDefinitionQuery()
						.processDefinitionName((String) execution.getVariable("definitionName")).deploymentId(deploymentId).latestVersion()
						.singleResult();
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
