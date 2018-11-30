package notificationservice;

import org.camunda.bpm.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

public class NotificationListener extends AbstractBpmnParseListener {

  @Override
  public void parseUserTask(Element userTaskElement, ScopeImpl scope,
      ActivityImpl activity) {
    
    // get current behavior
    ActivityBehavior activityBehavior = activity.getActivityBehavior();
    
    // only if it is user task ?
    if (activityBehavior instanceof UserTaskActivityBehavior) {
      
      //add custom listener to it
      UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
      userTaskActivityBehavior.getTaskDefinition().addTaskListener("create",
          NotificationTaskListener.getInstance());
    }
  }
}
