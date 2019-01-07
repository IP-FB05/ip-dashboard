package fhaachen.ip.viewcampusdashboard.masterarbeit;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class WiederholungKolSetter implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		execution.setVariable("finalNo", true);

	}

}
