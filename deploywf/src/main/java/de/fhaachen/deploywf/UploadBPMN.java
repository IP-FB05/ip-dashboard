package de.fhaachen.deploywf;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.FileValue;

public class UploadBPMN implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
			"http://localhost:8081/uploadStream");
		postRequest.addHeader("accept", "application/json");
		postRequest.addHeader("Authorization", "Basic YWRtaW46RDQ1aGIwYXJk");
		
		FileValue retrievedTypedFileValueBPMN = execution.getProcessEngineServices().getRuntimeService().getVariableTyped(execution.getId(), "bpmn");
		InputStream fileContentBPMN = retrievedTypedFileValueBPMN.getValue(); // a byte stream of the file contents
		InputStreamEntity entity = new InputStreamEntity(fileContentBPMN);
		
		postRequest.setEntity(entity);
		
		HttpResponse response = httpClient.execute(postRequest);
		
		if (response.getStatusLine().getStatusCode() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));

		String path = br.readLine();
		execution.setVariable("bpmnpath", path);
		
	}

}
