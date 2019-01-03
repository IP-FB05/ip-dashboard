package fhaachen.ip.viewcampusdashboard.bachelorarbeit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DateParser implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		Date date = (Date) execution.getVariable("InputDate");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        String strDate = dateFormat.format(date);
		
		execution.setVariable("OutputDate", strDate);
		
		
	}

}
