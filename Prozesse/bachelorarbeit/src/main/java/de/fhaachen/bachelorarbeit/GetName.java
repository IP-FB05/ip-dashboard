package de.fhaachen.bachelorarbeit;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class GetName implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String initiator = (String) execution.getVariable("initiator");  
        
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();            
        
        String name = processEngine.getIdentityService().createUserQuery()
				.userId(initiator).singleResult().getLastName();
        
        execution.setVariable("stdName", name);

	}

}
