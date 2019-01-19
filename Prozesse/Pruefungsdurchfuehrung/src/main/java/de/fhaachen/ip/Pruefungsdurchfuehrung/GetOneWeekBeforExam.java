package de.fhaachen.ip.Pruefungsdurchfuehrung;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class GetOneWeekBeforExam implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		Date pruefungsdatum = (Date)execution.getVariable("pruefungsdatum");

		Calendar c = Calendar.getInstance();
		c.setTime(pruefungsdatum);
		c.add(Calendar.DATE, -7);

		Date predate = c.getTime();
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(predate);

		execution.setVariable("predate", date);
	}
}