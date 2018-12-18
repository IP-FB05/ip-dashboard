package fhaachen.ip.viewcampusdashboard.bachelorarbeit;

import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class VerlaengerungSetter implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Date verlaengerungsDate = (Date) execution.getVariable("verlaengerung");
		
		execution.setVariable("startDatum", verlaengerungsDate);

	}

}