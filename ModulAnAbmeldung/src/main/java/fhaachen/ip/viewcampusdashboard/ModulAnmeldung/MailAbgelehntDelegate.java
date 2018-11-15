package fhaachen.ip.viewcampusdashboard.ModulAnmeldung;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateVariableMapping;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;

public class MailAbgelehntDelegate implements JavaDelegate, DelegateVariableMapping {

	String subjectString;
	String messageString;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		SendMail mailclass = new SendMail();
		mailclass.send(subjectString, messageString);

	}

	@Override
	public void mapInputVariables(DelegateExecution superExecution, VariableMap subVariables) {
		subVariables.putValue("subjectString", "subjectInput");
		subVariables.putValue("messageString", "messageInput");
		
	}

	@Override
	public void mapOutputVariables(DelegateExecution superExecution, VariableScope subInstance) {
		// Leer
		
	}

}
