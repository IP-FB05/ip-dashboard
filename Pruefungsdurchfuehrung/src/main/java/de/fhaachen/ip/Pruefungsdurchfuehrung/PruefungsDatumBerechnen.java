package de.fhaachen.ip.Pruefungsdurchfuehrung;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import java.util.Date;

public class PruefungsDatumBerechnen {

    public void execute(DelegateExecution execution) throws Exception {
        Object Datum = execution.getVariable("pruefungsdatum");
        execution.setVariable("ttt",Datum);

    }
}
