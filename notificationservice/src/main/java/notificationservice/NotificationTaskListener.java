package notificationservice;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class NotificationTaskListener implements TaskListener {

  private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
  public static List<String> assigneeList = new ArrayList<String>();

  private static NotificationTaskListener instance = null;

  protected NotificationTaskListener() { }

  public static NotificationTaskListener getInstance() {
    if(instance == null) {
      instance = new NotificationTaskListener();
    }
    return instance;
  }

  public void notify(DelegateTask delegateTask) {
    delegateTask.getProcessDefinitionId();
    delegateTask.getProcessInstanceId();
    //TODO!!!
    //1. in der DB schauen, ob dieser Task notifikationswürdig ist, falls nein, abbrechen
    //2. alle subscriber der prozessdefinition aus der DB holen 
    //3. alle prozessbeteiligten der Prozessinstanz aus der DB holen
    //4. allen beteiligten und subscribern nachrichten verschicken
  }

}