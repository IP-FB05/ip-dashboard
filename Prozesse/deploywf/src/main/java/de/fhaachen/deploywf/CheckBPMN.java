package de.fhaachen.deploywf;

import java.io.InputStream;
import java.util.Collection;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.ModelParseException;

public class CheckBPMN implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		// BPMN holen
		FileValue retrievedTypedFileValueBPMN = execution.getProcessEngineServices().getRuntimeService().getVariableTyped(execution.getId(), "bpmn");
		InputStream fileContentBPMN = retrievedTypedFileValueBPMN.getValue(); // a byte stream of the file contents
		try {
			// versuche es zu parsen
			BpmnModelInstance model = Bpmn.readModelFromStream(fileContentBPMN);
			Collection<Process> processes = model.getModelElementsByType(Process.class);
			if(processes.isEmpty()) {
				throw new ModelParseException("Kein Prozess im BPMN");
			}
			Process p = processes.iterator().next();

			execution.setVariable("groups", p.getCamundaCandidateStarterGroupsList());
			execution.setVariable("groupsJSON", "");
			
			execution.setVariable("definitionId", p.getId());
			execution.setVariable("definitionName", p.getName());
			execution.setVariable("bpmnsuccess", true);
			
		}catch(ModelParseException e){
			//Konnte nicht geparst werden
			execution.setVariable("errorlog", "File konnte nicht geparst werden: " + e.getMessage());
			execution.setVariable("bpmnsuccess", false);
		}catch(Exception e) {
			//
			execution.setVariable("errorlog", "Fehler: " + e.getMessage());
			execution.setVariable("bpmnsuccess", false);
		}
		
	}

}
