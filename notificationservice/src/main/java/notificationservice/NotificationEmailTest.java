package notificationservice;

import org.camunda.bpm.engine.test.ProcessEngineTestCase;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;

public class NotificationEmailTest extends ProcessEngineTestCase {
	
	  @Deployment (resources="testprocess.bpmn")
		public void testModulAbmeldungProcess() {

		  runtimeService.startProcessInstanceByKey("testprocess");

		  Task task = taskService.createTaskQuery().singleResult();

		  taskService.complete(task.getId());

		}


}
