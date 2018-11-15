package fhaachen.ip.viewcampusdashboard.ModulAnmeldung;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class StudentEintragenDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		int modName = (int) execution.getVariable("modulname");
		String userid = (String) execution.getVariable("userID");

	}

}
