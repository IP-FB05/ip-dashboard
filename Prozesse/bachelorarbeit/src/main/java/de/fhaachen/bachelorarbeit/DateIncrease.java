package de.fhaachen.bachelorarbeit;

import java.util.Calendar;
import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DateIncrease implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		int weeksToAdd = Integer.parseInt((String) execution.getVariable("WeeksAdd"));
		Date startDate = (Date) execution.getVariable("startDatum");
		int daysToAdd = weeksToAdd*7;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);            
		calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
		Date endDate = calendar.getTime();

		execution.setVariable("endDatum", endDate);
	}
}