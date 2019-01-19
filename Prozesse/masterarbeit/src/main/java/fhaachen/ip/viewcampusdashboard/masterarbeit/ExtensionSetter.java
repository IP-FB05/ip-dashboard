package fhaachen.ip.viewcampusdashboard.masterarbeit;

import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ExtensionSetter implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Date verlaengerungsDate = (Date) execution.getVariable("verlaengerung");

		execution.setVariable("endDatum", verlaengerungsDate);
	}
}