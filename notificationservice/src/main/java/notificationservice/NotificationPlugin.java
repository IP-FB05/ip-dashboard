package notificationservice;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

public class NotificationPlugin  extends AbstractProcessEnginePlugin {

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
      List<BpmnParseListener> preParseListeners = processEngineConfiguration.getCustomPreBPMNParseListeners();
      if(preParseListeners == null) {
        preParseListeners = new ArrayList<BpmnParseListener>();
        processEngineConfiguration.setCustomPreBPMNParseListeners(preParseListeners);
      }
      preParseListeners.add(new NotificationListener());
    }

  }