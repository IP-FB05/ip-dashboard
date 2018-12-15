package notificationservice;

import org.camunda.bpm.engine.test.ProcessEngineTestCase;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;

public class NotificationTest extends ProcessEngineTestCase {
	
	//Set the Demo User Email Address 
		private static final String EMAIL = "testip@gmail.com"; //demo@example.org


	  @Deployment (resources="NotificationEmail.bpmn")
		public void testSimpleProcess() {

			// Create the user that will be informed on assignment
		  User newUser = identityService.newUser("demo");
			newUser.setEmail(EMAIL);
			identityService.saveUser(newUser);
			
			runtimeService.startProcessInstanceByKey("NotificationEmail");

		  Task task = taskService.createTaskQuery().singleResult();

		  taskService.complete(task.getId());

		}


}
